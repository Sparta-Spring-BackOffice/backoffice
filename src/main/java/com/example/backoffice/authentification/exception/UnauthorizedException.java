package com.example.backoffice.authentification.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends AuthentificationException {
    public UnauthorizedException(AuthErrorCode authErrorCode) {
        super(HttpStatus.UNAUTHORIZED, authErrorCode);
    }
}