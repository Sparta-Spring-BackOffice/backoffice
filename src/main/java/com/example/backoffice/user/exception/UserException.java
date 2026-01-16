package com.example.backoffice.user.exception;

import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private HttpStatus status;
    public ErrorCode errorCode;

    public UserException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }
}
