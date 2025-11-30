// CourseDTO.java
package com.jspm.SmartErp.dto;

public class CourseDTO {
    private Integer courseId;
    private String courseName;
    private String department;
    private Integer semester;
    private String description;

    // Default constructor
    public CourseDTO() {}

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
    public void setDescription(String courseCode) { this.description = description; }
}