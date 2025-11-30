// CourseController.java
package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.dto.CourseDTO;
import com.jspm.SmartErp.model.Course;
import com.jspm.SmartErp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        try {
            List<CourseDTO> courses = courseService.getAllCourses()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getCourseName());
        dto.setDepartment(course.getDepartment());
        dto.setSemester(course.getSemester());
        dto.setDescription(course.getDiscription());
        return dto;
    }
}