package com.example.backoffice.admin.exception;

import com.example.backoffice.common.exception.ServiceException;
import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AdminException extends ServiceException {
    public AdminException(HttpStatus status, ErrorCode errorCode) {
        super(status, errorCode);
    }
}
