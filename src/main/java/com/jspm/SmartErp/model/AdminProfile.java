package com.jspm.SmartErp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProfile {

    @Id
    private Integer adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private User user;

    private String fullName;
    private String phone;
}
