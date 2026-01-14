package com.example.backoffice.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneAdminResponse {
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;

    public GetOneAdminResponse(String name, String email, String phone, String role, String status, LocalDateTime createdAt, LocalDateTime approvedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }
}
