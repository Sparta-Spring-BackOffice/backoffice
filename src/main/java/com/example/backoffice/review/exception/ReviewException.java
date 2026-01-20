package com.example.backoffice.review.exception;

import com.example.backoffice.common.exception.ServiceException;
import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReviewException extends ServiceException {
    public ReviewException(HttpStatus status, ErrorCode errorCode) {
        super(status, errorCode);
    }
}
