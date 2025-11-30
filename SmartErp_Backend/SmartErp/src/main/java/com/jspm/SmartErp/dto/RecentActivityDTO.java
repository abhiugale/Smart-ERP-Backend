package com.jspm.SmartErp.dto;

public class RecentActivityDTO {
    private Integer id;
    private String type;
    private String message;
    private String time;

    // Constructors
    public RecentActivityDTO() {}

    public RecentActivityDTO(Integer id, String type, String message, String time) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.time = time;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}