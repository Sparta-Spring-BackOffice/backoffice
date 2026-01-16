package com.example.backoffice.order.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class OutOfStockException extends OrderException{
    public OutOfStockException(ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, errorCode);
    }
}
