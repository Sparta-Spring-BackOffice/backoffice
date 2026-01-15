package com.example.backoffice.authentification.exception;

import com.example.backoffice.admin.consts.DeclineReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginDeniedException extends AuthentificationException {

    private final DeclineReason declineReason;

    public LoginDeniedException(AuthErrorCode authErrorCode, DeclineReason declineReason) {
        super(HttpStatus.UNAUTHORIZED, authErrorCode);
        this.declineReason = declineReason;
    }

    public DeclineReason getDeclineReason() {
        return declineReason;
    }
}
