package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.Faculty;
import com.jspm.SmartErp.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public List<Faculty> findAll() {
        List<Faculty> faculties = facultyRepository.findAll();
        System.out.println("📊 FacultyService: Found " + faculties.size() + " faculties");
        return faculties;
    }

    public Optional<Faculty> findById(Integer id) {
        System.out.println("🔍 FacultyService: Finding faculty by ID: " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        System.out.println("✅ FacultyService: Faculty found: " + faculty.isPresent());
        return faculty;
    }

    public Faculty findByEmpNumber(String empNumber) {
        System.out.println("🔍 FacultyService: Finding faculty by employee number: " + empNumber);
        return facultyRepository.findByEmpNumber(empNumber)
                .orElseThrow(() -> {
                    System.err.println("❌ FacultyService: Faculty not found with employee number: " + empNumber);
                    return new RuntimeException("Faculty not found with employee number: " + empNumber);
                });
    }

    public Faculty findByEmail(String email) {
        return facultyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));
    }

    public List<Faculty> findByDepartment(String department) {
        return facultyRepository.findByDepartment(department);
    }

    public Faculty save(Faculty faculty) {
        // Always encode password before saving
        if (faculty.getPassword() != null && !isPasswordEncoded(faculty.getPassword())) {
            String encodedPassword = passwordEncoder.encode(faculty.getPassword());
            faculty.setPassword(encodedPassword);
            System.out.println("🔐 Password encoded for faculty: " + faculty.getEmail());
        }

        Faculty savedFaculty = facultyRepository.save(faculty);
        System.out.println("💾 FacultyService: Faculty saved: " + savedFaculty.getEmail());
        return savedFaculty;
    }

    public void deleteById(Integer id) {
        System.out.println("🗑️ FacultyService: Deleting faculty ID: " + id);
        facultyRepository.deleteById(id);
    }

    private boolean isPasswordEncoded(String password) {
        return password != null && password.startsWith("$2a$");
    }

    public boolean existsByEmpNumber(String empNumber) {
        return facultyRepository.existsByEmpNumber(empNumber);
    }

    public boolean existsByEmail(String email) {
        return facultyRepository.existsByEmail(email);
    }
}