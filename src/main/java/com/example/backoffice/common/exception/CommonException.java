package com.example.backoffice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;

    public CommonException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = status;
    }
}
