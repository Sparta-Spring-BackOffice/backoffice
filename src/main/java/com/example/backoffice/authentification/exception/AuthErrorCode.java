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
    LOGIN_ERROR("LOGIN_ERROR", "비밀번호 또는 이메일이 불일치합니다."),
    NOT_LOGIN("NOT_LOGIN", "로그인이 필요합니다."),
    PASSWORD_MISMATCH_ERROR("PASSWORD_MISMATCH_ERROR", "현재 비밀번호가 일치하지 않습니다."),
    TOKEN_MISSING("TOKEN_MISSING", "인증 토큰이 없습니다."),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "토큰이 만료되었습니다."),
    TOKEN_INVALID_SIGNATURE("TOKEN_INVALID_SIGNATURE", "토큰 서명이 유효하지 않습니다."),
    TOKEN_MALFORMED("TOKEN_MALFORMED", "토큰 형식이 올바르지 않습니다."),
    TOKEN_UNSUPPORTED("TOKEN_UNSUPPORTED", "지원하지 않는 토큰입니다."),
    TOKEN_INVALID("TOKEN_INVALID", "유효하지 않은 토큰입니다.");


    private final String code;
    private final String message;
}
