package com.example.backoffice.admin.consts;

import lombok.Getter;

@Getter
public enum DeclineReason {
    UNAUTHORIZED("권한이 승인 기준에 부합하지 않습니다."),
    ADMIN_EXCESS("관리자 정원이 초과되었습니다."),
    TIME_OUT("승인 처리 시간이 초과되었습니다.");

    private final String message;

    DeclineReason(String message) {
        this.message = message;
    }
}
