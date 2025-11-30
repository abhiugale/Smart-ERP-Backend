package com.jspm.SmartErp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class LoginRequest {
    private String email;
    private String password;

    // Constructors
    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExamRequestDTO {
        private String examType;
        private LocalDate examDate;
        private String startTime;
        private String endTime;
        private String roomNumber;
        private Integer totalMarks;
        private CourseDTO course;
    }
}