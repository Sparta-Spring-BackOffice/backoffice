package com.example.backoffice.product.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends RuntimeException {

    public HttpStatus status;
    public ErrorCode errorCode;

    public ProductException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = httpStatus;
        this.errorCode = errorCode;
    }
}
