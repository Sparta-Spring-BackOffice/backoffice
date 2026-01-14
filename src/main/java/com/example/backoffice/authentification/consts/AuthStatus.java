package com.example.backoffice.authentification.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthStatus {

    LOGIN_SUCCESS("Login 완료");

    private final String message;
}
