package com.jspm.SmartErp.dto;

public class DashboardStatsDTO {
    private Integer totalCourses;
    private Integer averageScore;
    private Integer pendingAssignments;
    private Integer upcomingExams;

    // Default constructor
    public DashboardStatsDTO() {
    }

    // Parameterized constructor
    public DashboardStatsDTO(Integer totalCourses, Integer averageScore, Integer pendingAssignments, Integer upcomingExams) {
        this.totalCourses = totalCourses;
        this.averageScore = averageScore;
        this.pendingAssignments = pendingAssignments;
        this.upcomingExams = upcomingExams;
    }

    // Getters and Setters
    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getPendingAssignments() {
        return pendingAssignments;
    }

    public void setPendingAssignments(Integer pendingAssignments) {
        this.pendingAssignments = pendingAssignments;
    }

    public Integer getUpcomingExams() {
        return upcomingExams;
    }

    public void setUpcomingExams(Integer upcomingExams) {
        this.upcomingExams = upcomingExams;
    }

    // toString method
    @Override
    public String toString() {
        return "DashboardStatsDTO{" +
                "totalCourses=" + totalCourses +
                ", averageScore=" + averageScore +
                ", pendingAssignments=" + pendingAssignments +
                ", upcomingExams=" + upcomingExams +
                '}';
    }
}