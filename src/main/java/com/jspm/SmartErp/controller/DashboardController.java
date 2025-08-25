package com.jspm.SmartErp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    // Admin-only endpoint
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        System.out.println("Admin dashboard accessed");
        return "Welcome to Admin Dashboard!";
    }

    // Faculty-only endpoint
    @GetMapping("/faculty/dashboard")
    public String facultyDashboard() {
        return "Welcome to Faculty Dashboard!";
    }

    // Student-only endpoint
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "Welcome to Student Dashboard!";
    }
}
