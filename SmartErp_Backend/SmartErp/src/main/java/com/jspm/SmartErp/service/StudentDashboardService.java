// StudentDashboardService.java
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.dto.DashboardStatsDTO;
import com.jspm.SmartErp.dto.RecentActivityDTO;
import com.jspm.SmartErp.model.Student;
import com.jspm.SmartErp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDashboardService {

    @Autowired
    private StudentRepository studentRepository;

    public DashboardStatsDTO getStudentDashboardStats(Integer studentId) {
        // For now, return mock stats
        // You can replace this with real data later
        return new DashboardStatsDTO(6, 85, 3, 2);
    }

    public List<RecentActivityDTO> getRecentActivities(Integer studentId) {
        // Return mock activities
        return getMockActivities();
    }

    private List<RecentActivityDTO> getMockActivities() {
        List<RecentActivityDTO> activities = new ArrayList<>();

        activities.add(new RecentActivityDTO(1, "assignment", "Assignment 3 submitted", "2 hours ago"));
        activities.add(new RecentActivityDTO(2, "quiz", "Quiz 1 completed - Score: 18/20", "1 day ago"));
        activities.add(new RecentActivityDTO(3, "exam", "Midterm exam scheduled", "2 days ago"));
        activities.add(new RecentActivityDTO(4, "attendance", "Attendance marked for CS101", "3 days ago"));

        return activities;
    }
}