package com.example.backoffice.authentification.dto;

import com.example.backoffice.admin.consts.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdmin {
    private final Long id;
    private final String email;
    private final AdminRole role;

    public SessionAdmin(Long id, String email, AdminRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
