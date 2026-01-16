package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class OutOfStockException extends OrderException{
    public OutOfStockException(ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, errorCode);
    }
}
