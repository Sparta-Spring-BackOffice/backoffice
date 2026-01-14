package com.example.backoffice.authentification.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class LoginStatusException extends AuthentificationException {
    public LoginStatusException(AuthErrorCode authErrorCode) {
        super(HttpStatus.FORBIDDEN, authErrorCode);
    }
}
