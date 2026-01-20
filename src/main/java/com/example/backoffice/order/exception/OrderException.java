package com.example.backoffice.order.exception;

import com.example.backoffice.common.exception.ServiceException;
import com.example.backoffice.common.responsecode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class OrderException extends ServiceException {
    public OrderException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
