package com.example.backoffice.admin.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoSuchAdminStatusException extends AdminException {
    public NoSuchAdminStatusException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
