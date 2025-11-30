package com.jspm.SmartErp.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class FacultyDTO {
    private Integer facultyId;

    @NotBlank(message = "Employee number is required")
    private String empNumber;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotNull(message = "Join date is required")
    private LocalDate joinDate;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Constructors
    public FacultyDTO() {}

    // Getters and Setters
    public Integer getFacultyId() { return facultyId; }
    public void setFacultyId(Integer facultyId) { this.facultyId = facultyId; }

    public String getEmpNumber() { return empNumber; }
    public void setEmpNumber(String empNumber) { this.empNumber = empNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}