package com.example.backoffice.authentification.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotLoginException extends AuthentificationException {
    public NotLoginException(AuthErrorCode authErrorCode) {
        super(HttpStatus.UNAUTHORIZED, authErrorCode);
    }
}