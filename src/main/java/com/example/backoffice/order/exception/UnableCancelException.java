package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnableCancelException extends OrderException{
    public UnableCancelException(ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, errorCode);
    }
}
