package com.example.backoffice.admin.dto;

import lombok.Getter;

@Getter
public class CreateAdminResponse {
    private final String code;
    private final String message;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final String createdAt;

    public CreateAdminResponse(String code, String message, String name, String email, String phone, String role, String status, String createdAt) {
        this.code = code;
        this.message = message;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }
}
