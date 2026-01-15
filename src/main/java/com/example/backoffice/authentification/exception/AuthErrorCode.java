package com.example.backoffice.authentification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    LOGIN_PENDING_ERROR("LOGIN_PENDING_ERROR", "계정이 승인 대기 중입니다."),
    LOGIN_DENIED_ERROR("LOGIN_DENIED_ERROR", "승인이 거부되었습니다."),
    LOGIN_SUSPENDED_ERROR("LOGIN_SUSPENDED_ERROR", "정지된 계정입니다."),
    LOGIN_NON_ACTIVE_ERROR("LOGIN_NON_ACTIVE_ERROR", "비활성화된 계정입니다."),
    ALREADY_LOGIN("ALREADY_LOGIN", "이미 로그인된 사용자 입니다."),
    LOGIN_ERROR("LOGIN_ERROR", "비밀번호 또는 이메일이 불일치합니다.");

    private final String code;
    private final String message;
}
