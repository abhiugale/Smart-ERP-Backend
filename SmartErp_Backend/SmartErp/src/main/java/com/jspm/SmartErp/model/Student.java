package com.jspm.SmartErp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {
    private String name;
    private String studentPrn;
    private String department;
    private Integer semester;
    private String section;
    private String grade;
    private LocalDate admissionDate;

    // Constructors
    public Student() {}

    public Student(String password, String email, String phone, String name,
                   String studentPrn, String department, Integer semester,
                   String section, LocalDate admissionDate) {
        super(password, email, phone, Role.STUDENT);
        this.name = name;
        this.studentPrn = studentPrn;
        this.department = department;
        this.semester = semester;
        this.section = section;
        this.admissionDate = admissionDate;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentPrn() { return studentPrn; }
    public void setStudentPrn(String studentPrn) { this.studentPrn = studentPrn; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
}