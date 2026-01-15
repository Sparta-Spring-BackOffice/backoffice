package com.example.backoffice.authentification.exception;

import org.springframework.http.HttpStatus;

public class NotLoginException extends AuthentificationException {
    public NotLoginException(AuthErrorCode authErrorCode) {
        super(HttpStatus.UNAUTHORIZED, authErrorCode);
    }
}
