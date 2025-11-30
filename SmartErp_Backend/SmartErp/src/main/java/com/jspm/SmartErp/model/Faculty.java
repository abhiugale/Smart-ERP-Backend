// Faculty.java
package com.jspm.SmartErp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "faculty")
@PrimaryKeyJoinColumn(name = "faculty_id")
public class Faculty extends User {

    @Column(unique = true, nullable = false)
    private String empNumber;
    private String fullName;
    private String department;
    private String qualification;
    private LocalDate joinDate;

    // Constructors
    public Faculty() {}

    public Faculty(String password, String email, String phone, String empNumber,
                   String fullName, String department, String qualification, LocalDate joinDate) {
        super(password, email, phone, Role.FACULTY);
        this.empNumber = empNumber;
        this.fullName = fullName;
        this.department = department;
        this.qualification = qualification;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public String getEmpNumber() { return empNumber; }
    public void setEmpNumber(String empNumber) { this.empNumber = empNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    // Convenience method to get the faculty ID from the parent User class
    public Integer getFacultyId() {
        return this.getUserId();
    }
}