package com.example.backoffice.admin.entity;

import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.admin.consts.AdminStatus;
import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.common.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "administrators")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private LocalDateTime approvedAt;
    @Enumerated(EnumType.STRING)
    private AdminRole role;
    @Enumerated(EnumType.STRING)
    private AdminStatus status;
    @Enumerated(EnumType.STRING)
    private DeclineReason declineFor;

    public Administrator(String name, String email, String password, String phone, AdminRole adminRole, AdminStatus adminStatus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = adminRole;
        this.status = adminStatus;
    }

    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void activeAdmin() {
        status = AdminStatus.ACTIVE;
        approvedAt = LocalDateTime.now();
    }

    public void deniedAdmin(DeclineReason declineReason) {
        status = AdminStatus.DENIED;
        declineFor = declineReason;
    }
}
