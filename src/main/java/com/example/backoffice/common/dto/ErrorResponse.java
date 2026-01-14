package com.example.backoffice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private String path;

    public static ErrorResponse of(HttpStatus status, String code, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status.value(), code, message, path);
    }
}
