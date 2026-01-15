package com.example.backoffice.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends CommonException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
