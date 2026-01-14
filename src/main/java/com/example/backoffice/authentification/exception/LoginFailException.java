package com.example.backoffice.authentification.exception;

import org.springframework.http.HttpStatus;

public class LoginFailException extends AuthentificationException {
    public LoginFailException(AuthErrorCode authErrorCode) {
        super(HttpStatus.FORBIDDEN, authErrorCode);
    }
}
