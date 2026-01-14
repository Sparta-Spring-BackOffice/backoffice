package com.example.backoffice.admin.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoSuchAdminStatus extends AdminException {
    public NoSuchAdminStatus(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
