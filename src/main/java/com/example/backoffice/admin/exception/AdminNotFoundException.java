package com.example.backoffice.admin.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends AdminException {
    public AdminNotFoundException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
