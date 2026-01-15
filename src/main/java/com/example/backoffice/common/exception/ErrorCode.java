package com.example.backoffice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ROLE("NO_SUCH_ROLE", "존재하지 않는 관리자 역할입니다."),
    NO_SUCH_STATUS("NO_SUCH_STATUS", "존재하지 않는 관리자 상태입니다."),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    NO_SUCH_USER("NO_SUCH_USER", "존재하지 않는 유저입니다."),
    ADMIN_NOT_FOUND("ADMIN_NOT_FOUND", "관리자가 존재하지 않습니다."),
    INSUFFICIENT_ADMIN_ROLE ("INSUFFICIENT_ADMIN_ROLE", "필요한 관리자 역할이 없습니다"),
    PASSWORD_CONFIRM_MISMATCH_ERROR("PASSWORD_CONFIRM_MISMATCH_ERROR", "새 비밀번호가 일치하지 않습니다.");
    private final String code;
    private final String message;
}
