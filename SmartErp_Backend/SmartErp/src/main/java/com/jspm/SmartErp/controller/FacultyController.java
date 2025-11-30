package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.dto.FacultyDTO;
import com.jspm.SmartErp.model.Faculty;
import com.jspm.SmartErp.service.FacultyService;
import com.jspm.SmartErp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculties")
@Validated
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    private FacultyDTO convertToDTO(Faculty faculty) {
        FacultyDTO dto = new FacultyDTO();
        dto.setFacultyId(faculty.getUserId());
        dto.setEmpNumber(faculty.getEmpNumber());
        dto.setFullName(faculty.getFullName());
        dto.setEmail(faculty.getEmail());
        dto.setPhone(faculty.getPhone());
        dto.setDepartment(faculty.getDepartment());
        dto.setQualification(faculty.getQualification());
        dto.setJoinDate(faculty.getJoinDate());
        return dto;
    }

    // GET all faculties
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        try {
            System.out.println("📋 Fetching all faculties...");
            List<Faculty> faculties = facultyService.findAll();
            System.out.println("✅ Found " + faculties.size() + " faculties");

            List<FacultyDTO> facultyDTOs = faculties.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(facultyDTOs);
        } catch (Exception e) {
            System.err.println("❌ Error fetching faculties: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET faculty by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Integer id) {
        try {
            System.out.println("🔍 Fetching faculty with ID: " + id);
            Faculty faculty = facultyService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
            return ResponseEntity.ok(convertToDTO(faculty));
        } catch (Exception e) {
            System.err.println("❌ Error fetching faculty by ID: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // GET faculty by employee number
    @GetMapping("/emp-number/{empNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyDTO> getFacultyByEmpNumber(@PathVariable String empNumber) {
        try {
            System.out.println("🔍 Fetching faculty with employee number: " + empNumber);
            Faculty faculty = facultyService.findByEmpNumber(empNumber);
            return ResponseEntity.ok(convertToDTO(faculty));
        } catch (RuntimeException e) {
            System.err.println("❌ Faculty not found with employee number: " + empNumber);
            return ResponseEntity.notFound().build();
        }
    }

    // GET faculties by department
    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FacultyDTO>> getFacultiesByDepartment(@PathVariable String department) {
        try {
            System.out.println("🏫 Fetching faculties in department: " + department);
            List<Faculty> faculties = facultyService.findByDepartment(department);
            List<FacultyDTO> facultyDTOs = faculties.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(facultyDTOs);
        } catch (Exception e) {
            System.err.println("❌ Error fetching faculties by department: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // POST create faculty
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFaculty(@Valid @RequestBody FacultyDTO facultyDTO) {
        try {
            System.out.println("➕ Creating new faculty: " + facultyDTO.getEmail());

            // Check if email already exists
            if (userService.existsByEmail(facultyDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email '" + facultyDTO.getEmail() + "' is already registered");
            }

            // Check if employee number already exists
            if (facultyService.existsByEmpNumber(facultyDTO.getEmpNumber())) {
                return ResponseEntity.badRequest().body("Employee number '" + facultyDTO.getEmpNumber() + "' is already registered");
            }

            // Create new faculty
            Faculty faculty = new Faculty(
                    facultyDTO.getPassword(),
                    facultyDTO.getEmail(),
                    facultyDTO.getPhone(),
                    facultyDTO.getEmpNumber(),
                    facultyDTO.getFullName(),
                    facultyDTO.getDepartment(),
                    facultyDTO.getQualification(),
                    facultyDTO.getJoinDate()
            );
            faculty.setEmpNumber(facultyDTO.getEmpNumber());

            Faculty savedFaculty = facultyService.save(faculty);
            System.out.println("✅ Faculty created: " + savedFaculty.getEmail());

            return ResponseEntity.ok(convertToDTO(savedFaculty));

        } catch (Exception e) {
            System.err.println("❌ Error creating faculty: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating faculty: " + e.getMessage());
        }
    }

    // PUT update faculty
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFaculty(@PathVariable Integer id, @Valid @RequestBody FacultyDTO facultyDTO) {
        try {
            System.out.println("✏️ Updating faculty ID: " + id);

            Faculty existingFaculty = facultyService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));

            // Check if email is being changed and already exists
            if (!existingFaculty.getEmail().equals(facultyDTO.getEmail()) &&
                    userService.existsByEmail(facultyDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email '" + facultyDTO.getEmail() + "' is already registered");
            }

            // Check if employee number is being changed and already exists
            if (!existingFaculty.getEmpNumber().equals(facultyDTO.getEmpNumber()) &&
                    facultyService.existsByEmpNumber(facultyDTO.getEmpNumber())) {
                return ResponseEntity.badRequest().body("Employee number '" + facultyDTO.getEmpNumber() + "' is already registered");
            }

            // Update fields
            existingFaculty.setEmpNumber(facultyDTO.getEmpNumber());
            existingFaculty.setFullName(facultyDTO.getFullName());
            existingFaculty.setDepartment(facultyDTO.getDepartment());
            existingFaculty.setQualification(facultyDTO.getQualification());
            existingFaculty.setEmail(facultyDTO.getEmail());
            existingFaculty.setPhone(facultyDTO.getPhone());
            existingFaculty.setJoinDate(facultyDTO.getJoinDate());

            // Update password if provided
            if (facultyDTO.getPassword() != null && !facultyDTO.getPassword().isEmpty()) {
                existingFaculty.setPassword(facultyDTO.getPassword());
                System.out.println("🔑 Password update requested");
            }

            Faculty updatedFaculty = facultyService.save(existingFaculty);
            System.out.println("✅ Faculty updated: " + updatedFaculty.getEmail());

            return ResponseEntity.ok(convertToDTO(updatedFaculty));

        } catch (Exception e) {
            System.err.println("❌ Error updating faculty: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating faculty: " + e.getMessage());
        }
    }

    // DELETE faculty
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFaculty(@PathVariable Integer id) {
        try {
            System.out.println("🗑️ Deleting faculty ID: " + id);
            facultyService.deleteById(id);
            return ResponseEntity.ok().body("Faculty deleted successfully");
        } catch (Exception e) {
            System.err.println("❌ Error deleting faculty: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting faculty: " + e.getMessage());
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Faculties API is working! " + java.time.LocalDateTime.now());
    }
}