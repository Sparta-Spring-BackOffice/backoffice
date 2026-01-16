package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class OrderException extends RuntimeException {

    public HttpStatus httpStatus;
    public ErrorCode errorCode;

    public OrderException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
