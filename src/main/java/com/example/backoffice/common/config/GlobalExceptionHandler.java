package com.example.backoffice.common.config;

import com.example.backoffice.admin.exception.AdminException;
import com.example.backoffice.authentification.exception.AuthentificationException;
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
    @ExceptionHandler(AuthentificationException.class)
    public ResponseEntity<ErrorResponse> AuthentificationExceptionHandler(AuthentificationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getStatus(), e.getAuthErrorCode().getCode(), e.getAuthErrorCode().getMessage(), request.getRequestURI());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
}
