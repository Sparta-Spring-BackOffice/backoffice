package com.example.backoffice.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateAdminPasswordResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateAdminPasswordResponse(Long id, String name, String email, String phone, String role, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
