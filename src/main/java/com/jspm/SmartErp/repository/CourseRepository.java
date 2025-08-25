package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByDepartment(String department);
    List<Course> findBySemester(Integer semester);
}
