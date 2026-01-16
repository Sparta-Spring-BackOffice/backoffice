package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyCompletedException extends OrderException{
    public AlreadyCompletedException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
