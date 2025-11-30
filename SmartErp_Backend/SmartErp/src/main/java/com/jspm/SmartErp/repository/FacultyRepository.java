package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    Optional<Faculty> findByEmail(String email);
    Optional<Faculty> findByEmpNumber(String empNumber);
    boolean existsByEmail(String email);
    boolean existsByEmpNumber(String empNumber);
    List<Faculty> findByDepartment(String department);
}