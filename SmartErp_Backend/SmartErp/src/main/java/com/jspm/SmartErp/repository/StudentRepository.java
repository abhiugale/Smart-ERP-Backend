package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentPrn(String studentPrn);
    List<Student> findByDepartment(String department);
    List<Student> findBySemester(Integer semester);
    List<Student> findByDepartmentAndSemester(String department, Integer semester);
}