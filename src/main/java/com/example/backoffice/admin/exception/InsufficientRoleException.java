package com.example.backoffice.admin.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InsufficientRoleException extends AdminException {
    public InsufficientRoleException(ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode );
    }
}
