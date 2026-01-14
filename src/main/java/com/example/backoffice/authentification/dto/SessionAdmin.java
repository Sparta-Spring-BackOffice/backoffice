package com.example.backoffice.authentification.dto;

import com.example.backoffice.admin.consts.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdmin {
    private final Long id;
    private final String name;
    private final AdminRole role;

    public SessionAdmin(Long id, String name, AdminRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
