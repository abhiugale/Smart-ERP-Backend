package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    List<Faculty> findByDepartment(String department);
}
