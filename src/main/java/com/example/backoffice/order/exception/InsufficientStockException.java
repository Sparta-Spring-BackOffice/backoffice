package com.example.backoffice.order.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends OrderException{
    public InsufficientStockException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
