package com.example.backoffice.authentification.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthentificationException extends RuntimeException {
    public HttpStatus status;
    public AuthErrorCode authErrorCode;
    public AuthentificationException(HttpStatus status, AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.status = status;
        this.authErrorCode = authErrorCode;
    }
}
