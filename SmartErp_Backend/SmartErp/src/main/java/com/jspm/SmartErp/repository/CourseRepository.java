// CourseRepository.java
package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByDepartment(String department);
    List<Course> findBySemester(Integer semester);
    List<Course> findByDepartmentAndSemester(String department, Integer semester);
    boolean existsByCourseNameAndDepartment(String courseName, String department);
}