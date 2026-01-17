package com.example.backoffice.common.config;

import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.admin.exception.AdminException;
import com.example.backoffice.authentification.exception.AuthentificationException;
import com.example.backoffice.authentification.exception.LoginDeniedException;
import com.example.backoffice.common.dto.ErrorResponse;
import com.example.backoffice.common.exception.CommonException;
import com.example.backoffice.user.entity.User;
import com.example.backoffice.user.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse error = ErrorResponse.of(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", message,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(LoginDeniedException.class)
    public ResponseEntity<ErrorResponse> DeniedHandler(LoginDeniedException e, HttpServletRequest request) {
        String base = e.getAuthErrorCode().getMessage();
        String detail = (e.getDeclineReason() == null)
                ? "사유: 거부 사유가 등록되지 않았습니다."
                : e.getDeclineReason().getMessage();

        String msg = base + " 사유 : " + detail;

        ErrorResponse body = ErrorResponse.of(
                e.getStatus(),
                e.getAuthErrorCode().getCode(),
                msg,
                request.getRequestURI()
        );

        return ResponseEntity.status(e.getStatus()).body(body);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> CommonExceptionHandler(CommonException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getStatus(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(), request.getRequestURI());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> CommonExceptionHandler(UserException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getStatus(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(), request.getRequestURI());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

}
