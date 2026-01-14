package com.example.backoffice.common.config;

import com.example.backoffice.admin.exception.AdminException;
import com.example.backoffice.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ErrorResponse> adminExceptionHandler(AdminException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getStatus(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(), request.getRequestURI());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
}
