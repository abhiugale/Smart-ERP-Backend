package com.jspm.SmartErp.dto;

import com.jspm.SmartErp.model.Role;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer userId;
    private String email;
    private Role role;
    private String name;

    // Constructors
    public JwtResponse(String token, Integer userId, String email, Role role, String name) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.name = name;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}