package com.example.backoffice.jwt;

import com.example.backoffice.authentification.exception.AuthErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class JwtErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String code;
    private final String message;
    private final String path;

    public JwtErrorResponse(LocalDateTime timestamp, int status, String code, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;

    }

    public static JwtErrorResponse of( HttpStatus status, AuthErrorCode errorCode, String path) {
        return new JwtErrorResponse(LocalDateTime.now(), status.value(), errorCode.getCode(), errorCode.getMessage(), path);

    }
}
