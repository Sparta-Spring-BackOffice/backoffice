package com.example.backoffice.common.config;

import com.example.backoffice.admin.consts.DeclineReason;
import com.example.backoffice.admin.exception.AdminException;
import com.example.backoffice.authentification.exception.AuthentificationException;
import com.example.backoffice.authentification.exception.DeniedLoginFailException;
import com.example.backoffice.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
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
    @ExceptionHandler(DeniedLoginFailException.class)
    public ResponseEntity<ErrorResponse> handleDenied(DeniedLoginFailException e, HttpServletRequest request) {

        String base = e.getAuthErrorCode().getMessage();
        String reasonMsg = declineReasonMessage(e.getDeclineReason());

        String combined = base + " (" + reasonMsg + ")";

        ErrorResponse errorResponse = ErrorResponse.of(
                e.getStatus(),
                e.getAuthErrorCode().getCode(),
                combined,
                request.getRequestURI()
        );
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
    private String declineReasonMessage(DeclineReason reason) {
        return switch (reason) {
            case UNAUTHORIZED -> "사유: 권한이 승인 기준에 부합하지 않습니다.";
            case ADMIN_EXCESS -> "사유: 관리자 정원이 초과되었습니다.";
            case TIME_OUT -> "사유: 승인 처리 시간이 초과되었습니다.";
        };
    }
}
