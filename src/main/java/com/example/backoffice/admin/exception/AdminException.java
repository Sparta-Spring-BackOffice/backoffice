package com.example.backoffice.admin.exception;

import com.example.backoffice.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AdminException extends RuntimeException {
    public HttpStatus status;
    public ErrorCode errorCode;
    public AdminException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }
}
