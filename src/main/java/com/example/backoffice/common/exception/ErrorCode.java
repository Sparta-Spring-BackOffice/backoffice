package com.example.backoffice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ROLE("NO_SUCH_ROLE", "존재하지 않는 역할입니다.");

    private final String code;
    private final String message;
}
