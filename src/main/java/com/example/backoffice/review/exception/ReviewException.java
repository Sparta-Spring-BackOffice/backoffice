package com.example.backoffice.review.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReviewException extends RuntimeException {
    public HttpStatus httpStatus;
    public ErrorCode errorCode;
    public ReviewException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
