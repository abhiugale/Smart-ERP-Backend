package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.dto.DashboardStatsDTO;
import com.jspm.SmartErp.dto.RecentActivityDTO;
import com.jspm.SmartErp.dto.StudentDTO;
import com.jspm.SmartErp.model.Student;
import com.jspm.SmartErp.service.StudentDashboardService;
import com.jspm.SmartErp.service.StudentService;
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
@RequestMapping("/api/students")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentDashboardService studentDashboardService;

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getUserId());
        dto.setName(student.getName());
        dto.setStudentPrn(student.getStudentPrn());
        dto.setDepartment(student.getDepartment());
        dto.setSemester(student.getSemester());
        dto.setSection(student.getSection());
        dto.setGrade(student.getGrade());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        return dto;
    }

    // ✅ ADD THIS - GET all students endpoint
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try {
            System.out.println("📋 Fetching all students...");
            List<Student> students = studentService.findAll();
            System.out.println("✅ Found " + students.size() + " students");

            List<StudentDTO> studentDTOs = students.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(studentDTOs);
        } catch (Exception e) {
            System.err.println("❌ Error fetching students: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ ADD THIS - GET student by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        try {
            System.out.println("🔍 Fetching student with ID: " + id);
            Student student = studentService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
            return ResponseEntity.ok(convertToDTO(student));
        } catch (Exception e) {
            System.err.println("❌ Error fetching student by ID: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ ADD THIS - GET student by PRN
    @GetMapping("/prn/{prn}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<StudentDTO> getStudentByPrn(@PathVariable String prn) {
        try {
            System.out.println("🔍 Fetching student with PRN: " + prn);
            Student student = studentService.findByPrn(prn);
            return ResponseEntity.ok(convertToDTO(student));
        } catch (RuntimeException e) {
            System.err.println("❌ Student not found with PRN: " + prn);
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ ADD THIS - GET students by department
    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<StudentDTO>> getStudentsByDepartment(@PathVariable String department) {
        try {
            System.out.println("🏫 Fetching students in department: " + department);
            List<Student> students = studentService.findByDepartment(department);
            List<StudentDTO> studentDTOs = students.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(studentDTOs);
        } catch (Exception e) {
            System.err.println("❌ Error fetching students by department: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ ADD THIS - GET students by semester
    @GetMapping("/semester/{semester}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<StudentDTO>> getStudentsBySemester(@PathVariable Integer semester) {
        try {
            System.out.println("📚 Fetching students in semester: " + semester);
            List<Student> students = studentService.findBySemester(semester);
            List<StudentDTO> studentDTOs = students.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(studentDTOs);
        } catch (Exception e) {
            System.err.println("❌ Error fetching students by semester: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    // Get student dashboard statistics
    @GetMapping("/dashboard/{studentId}")
    public ResponseEntity<DashboardStatsDTO> getStudentDashboard(@PathVariable Integer studentId) {
        try {
            DashboardStatsDTO dashboardStats = studentDashboardService.getStudentDashboardStats(studentId);
            return ResponseEntity.ok(dashboardStats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get student profile - ONLY THIS ENDPOINT FOR STUDENT DETAILS
    @GetMapping("/{userId}")
    public ResponseEntity<Student> getStudentById(@PathVariable String userId) {
        try {
            Student student = studentService.findByPrn(userId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get recent activities for student
    @GetMapping("/{userID}/activities")
    public ResponseEntity<List<RecentActivityDTO>> getStudentActivities(@PathVariable Integer userId) {
        try {
            List<RecentActivityDTO> activities = studentDashboardService.getRecentActivities(userId);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST create student
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            System.out.println("➕ Creating new student: " + studentDTO.getEmail());

            // Check if email already exists
            if (userService.existsByEmail(studentDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email '" + studentDTO.getEmail() + "' is already registered");
            }

            // Check if PRN already exists
            try {
                Student existingByPrn = studentService.findByPrn(studentDTO.getStudentPrn());
                if (existingByPrn != null) {
                    return ResponseEntity.badRequest().body("PRN '" + studentDTO.getStudentPrn() + "' is already registered");
                }
            } catch (RuntimeException e) {
                // PRN doesn't exist, which is good - continue
            }

            // Create new student
            Student student = new Student(
                    studentDTO.getPassword(),
                    studentDTO.getEmail(),
                    studentDTO.getPhone(),
                    studentDTO.getName(),
                    studentDTO.getStudentPrn(),
                    studentDTO.getDepartment(),
                    studentDTO.getSemester(),
                    studentDTO.getSection(),
                    studentDTO.getAdmissionDate() != null ? studentDTO.getAdmissionDate() : java.time.LocalDate.now()
            );

            Student savedStudent = studentService.save(student);
            System.out.println("✅ Student created: " + savedStudent.getEmail());

            return ResponseEntity.ok(convertToDTO(savedStudent));

        } catch (Exception e) {
            System.err.println("❌ Error creating student: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating student: " + e.getMessage());
        }
    }

    // PUT update student
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO studentDTO) {
        try {
            System.out.println("✏️ Updating student ID: " + id);

            Student existingStudent = studentService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

            // Check if email is being changed and already exists
            if (!existingStudent.getEmail().equals(studentDTO.getEmail()) &&
                    userService.existsByEmail(studentDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email '" + studentDTO.getEmail() + "' is already registered");
            }

            // Check if PRN is being changed and already exists
            if (!existingStudent.getStudentPrn().equals(studentDTO.getStudentPrn())) {
                try {
                    Student existingByPrn = studentService.findByPrn(studentDTO.getStudentPrn());
                    if (existingByPrn != null && !existingByPrn.getUserId().equals(id)) {
                        return ResponseEntity.badRequest().body("PRN '" + studentDTO.getStudentPrn() + "' is already registered");
                    }
                } catch (RuntimeException e) {
                    // PRN doesn't exist, which is good - continue
                }
            }

            // Update fields
            existingStudent.setName(studentDTO.getName());
            existingStudent.setStudentPrn(studentDTO.getStudentPrn());
            existingStudent.setDepartment(studentDTO.getDepartment());
            existingStudent.setSemester(studentDTO.getSemester());
            existingStudent.setSection(studentDTO.getSection());
            existingStudent.setGrade(studentDTO.getGrade());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setPhone(studentDTO.getPhone());

            if (studentDTO.getAdmissionDate() != null) {
                existingStudent.setAdmissionDate(studentDTO.getAdmissionDate());
            }

            // Update password if provided
            if (studentDTO.getPassword() != null && !studentDTO.getPassword().isEmpty()) {
                existingStudent.setPassword(studentDTO.getPassword());
                System.out.println("🔑 Password update requested");
            }

            Student updatedStudent = studentService.save(existingStudent);
            System.out.println("✅ Student updated: " + updatedStudent.getEmail());

            return ResponseEntity.ok(convertToDTO(updatedStudent));

        } catch (Exception e) {
            System.err.println("❌ Error updating student: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating student: " + e.getMessage());
        }
    }

    // DELETE student
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        try {
            System.out.println("🗑️ Deleting student ID: " + id);
            studentService.deleteById(id);
            return ResponseEntity.ok().body("Student deleted successfully");
        } catch (Exception e) {
            System.err.println("❌ Error deleting student: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting student: " + e.getMessage());
        }
    }
}