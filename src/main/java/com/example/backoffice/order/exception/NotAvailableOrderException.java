package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotAvailableException extends OrderException{
    public NotAvailableException(ErrorCode errorCode) {
        super(HttpStatus.GONE, errorCode);
    }
}
