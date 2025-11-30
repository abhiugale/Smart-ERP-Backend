// ExamDTO.java
package com.jspm.SmartErp.dto;

import java.time.LocalDate;

public class ExamDTO {
    private Integer examId;
    private String examType;
    private LocalDate examDate;
    private String startTime;
    private String endTime;
    private String roomNumber;
    private Integer totalMarks;
    private CourseDTO course;

    // Default constructor
    public ExamDTO() {}

    // Getters and setters
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }

    public CourseDTO getCourse() { return course; }
    public void setCourse(CourseDTO course) { this.course = course; }
}