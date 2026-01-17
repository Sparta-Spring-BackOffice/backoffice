package com.example.backoffice.authentification.dto;

import com.example.backoffice.admin.consts.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdmin {
    private final Long id;
    private final String email;
    private final AdminRole role;
    private final String token;

    public SessionAdmin(Long id, String email, AdminRole role, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
