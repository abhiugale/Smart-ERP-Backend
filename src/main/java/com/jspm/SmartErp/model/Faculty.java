package com.jspm.SmartErp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "faculty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    private Integer facultyId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "faculty_id")
    private User user;

    private String fullName;
    private String department;
    private String phone;
    private String qualification;
    private LocalDate joinDate;
}
