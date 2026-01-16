package com.example.backoffice.order.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyCancelledException extends OrderException{
    public AlreadyCancelledException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
