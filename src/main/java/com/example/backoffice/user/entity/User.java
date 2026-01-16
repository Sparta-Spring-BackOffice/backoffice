package com.example.backoffice.user.entity;

import com.example.backoffice.common.config.BaseEntity;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.user.consts.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(String name, String email, String phoneNumber, UserStatus status) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public void updateUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateUserStatus(UserStatus status) {
        this.status = status;
    }

}
