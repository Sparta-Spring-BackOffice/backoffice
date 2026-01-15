package com.example.backoffice.admin.dto;

import lombok.Getter;

@Getter
public class GetAdminProfileResponse {
    private final String name;
    private final String email;
    private final String phone;

    public GetAdminProfileResponse(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
