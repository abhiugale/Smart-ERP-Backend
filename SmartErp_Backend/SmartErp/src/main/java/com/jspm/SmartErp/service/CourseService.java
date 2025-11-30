// CourseService.java
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.Course;
import java.util.List;

public interface CourseService {
    Course getCourseEntityById(Integer courseId);
    List<Course> getAllCourses();
}