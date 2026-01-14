package com.example.backoffice.common.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
}
