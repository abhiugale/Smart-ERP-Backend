package com.jspm.SmartErp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_profile")
@PrimaryKeyJoinColumn(name = "admin_id")
public class AdminProfile extends User {
    private String fullName;
    private String phone;

    // Constructors
    public AdminProfile() {}

    public AdminProfile(String password, String email, String phone, String fullName) {
        super(password, email, phone, Role.ADMIN);
        this.fullName = fullName;
        this.phone = phone;
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}