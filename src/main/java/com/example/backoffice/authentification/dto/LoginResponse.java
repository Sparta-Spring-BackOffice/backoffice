package com.example.backoffice.authentification.dto;


import com.example.backoffice.admin.consts.AdminRole;
import com.example.backoffice.authentification.consts.AuthStatus;
import lombok.Getter;


@Getter
public class LoginResponse {
    private final Long id;
    private final String email;
    private final AdminRole role;
    private final AuthStatus status;
    private final String token;

    public LoginResponse(Long id, String email, AdminRole role, AuthStatus status, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.token = token;
    }
}
