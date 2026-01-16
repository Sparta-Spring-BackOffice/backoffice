package com.example.backoffice.review.exception;

import com.example.backoffice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidRatingException extends ReviewException {
    public InvalidRatingException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
