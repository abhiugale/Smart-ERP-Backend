package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.Student;
import com.jspm.SmartErp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        List<Student> students = studentRepository.findAll();
        System.out.println("📊 StudentService: Found " + students.size() + " students");
        return students;
    }

    public Optional<Student> findById(Integer id) {
        System.out.println("🔍 StudentService: Finding student by ID: " + id);
        Optional<Student> student = studentRepository.findById(id);
        System.out.println("✅ StudentService: Student found: " + student.isPresent());
        return student;
    }

    public Student findByPrn(String studentPrn) {
        System.out.println("🔍 StudentService: Finding student by PRN: " + studentPrn);
        return studentRepository.findByStudentPrn(studentPrn)
                .orElseThrow(() -> {
                    System.err.println("❌ StudentService: Student not found with PRN: " + studentPrn);
                    return new RuntimeException("Student not found with PRN: " + studentPrn);
                });
    }

    public List<Student> findByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public List<Student> findBySemester(Integer semester) {
        return studentRepository.findBySemester(semester);
    }

    public List<Student> findByDepartmentAndSemester(String department, Integer semester) {
        return studentRepository.findByDepartmentAndSemester(department, semester);
    }

    public Student save(Student student) {
        // Always encode password before saving
        if (student.getPassword() != null && !isPasswordEncoded(student.getPassword())) {
            String encodedPassword = passwordEncoder.encode(student.getPassword());
            student.setPassword(encodedPassword);
            System.out.println("🔐 Password encoded for student: " + student.getEmail());
        }

        Student savedStudent = studentRepository.save(student);
        System.out.println("💾 StudentService: Student saved: " + savedStudent.getEmail());
        return savedStudent;
    }

    public void deleteById(Integer id) {
        System.out.println("🗑️ StudentService: Deleting student ID: " + id);
        studentRepository.deleteById(id);
    }

    private boolean isPasswordEncoded(String password) {
        return password != null && password.startsWith("$2a$");
    }


}