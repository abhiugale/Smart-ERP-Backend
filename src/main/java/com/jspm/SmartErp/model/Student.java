package com.jspm.SmartErp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    private String fullName;

    @Column(unique = true)
    private String rollNo;

    private String department;
    private Integer semester;
    private String phone;
    private LocalDate admissionDate;
}
