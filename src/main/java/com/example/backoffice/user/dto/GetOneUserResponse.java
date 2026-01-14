package com.example.backoffice.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneUserResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneUserResponse(Long id, String name, String email, String phoneNumber, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
