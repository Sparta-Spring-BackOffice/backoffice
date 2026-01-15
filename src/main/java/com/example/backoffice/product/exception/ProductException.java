package com.example.backoffice.product.exception;

import com.example.backoffice.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends RuntimeException {

    public HttpStatus httpStatus;
    public ErrorCode errorCode;

    public ProductException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
