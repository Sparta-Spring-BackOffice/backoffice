package com.example.backoffice.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateAdminResponse {
    private final String message;
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;


    public CreateAdminResponse(String message, Long id, String name, String email, String phone, String role, String status, LocalDateTime createdAt) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }
}
