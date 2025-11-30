// Course.java
package com.jspm.SmartErp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @Column(name = "description", unique = true)
    private String description;

    // Default constructor
    public Course() {}

    // Getters and setters
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public String getDiscription() { return description; }
    public void setDiscription(String description) { this.description = description; }
}