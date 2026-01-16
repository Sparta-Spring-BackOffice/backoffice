package com.example.backoffice.order.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotAvailableOrderException extends OrderException{
    public NotAvailableOrderException(ErrorCode errorCode) {
        super(HttpStatus.GONE, errorCode);
    }
}
