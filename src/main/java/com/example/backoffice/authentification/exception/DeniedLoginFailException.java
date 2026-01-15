package com.example.backoffice.authentification.exception;

import com.example.backoffice.admin.consts.DeclineReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeniedLoginFailException extends AuthentificationException {

    private final DeclineReason declineReason;

    public DeniedLoginFailException(AuthErrorCode authErrorCode, DeclineReason declineReason) {
        super(HttpStatus.UNAUTHORIZED, authErrorCode);
        this.declineReason = declineReason;
    }
}
