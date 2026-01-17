package com.example.backoffice.order.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class OrderException extends RuntimeException {

    public HttpStatus status;
    public ErrorCode errorCode;

    public OrderException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = httpStatus;
        this.errorCode = errorCode;
    }
}
