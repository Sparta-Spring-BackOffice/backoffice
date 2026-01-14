package com.example.backoffice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ROLE("NO_SUCH_ROLE", "존재하지 않는 관리자 역할입니다."),
    NO_SUCH_STATUS("NO_SUCH_STATUS", "존재하지 않는 관리자 상태입니다."),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    ADMIN_NOT_FOUND("ADMIN_NOT_FOUND", "관리자가 존재하지 않습니다.");
    private final String code;
    private final String message;
}
