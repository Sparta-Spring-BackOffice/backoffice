package com.example.backoffice.admin.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends AdminException {
    public AdminNotFoundException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
