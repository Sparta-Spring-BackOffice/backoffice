package com.example.backoffice.common;

import com.example.backoffice.admin.consts.DeclineReason;

public class DeclineReasonMessage {
    public static String toMessage(DeclineReason reason) {
        if (reason == null) {
            return "사유: 거부 사유가 등록되지 않았습니다.";
        }
        return switch (reason) {
            case UNAUTHORIZED -> "사유: 권한이 승인 기준에 부합하지 않습니다.";
            case ADMIN_EXCESS -> "사유: 관리자 정원이 초과되었습니다.";
            case TIME_OUT -> "사유: 승인 처리 시간이 초과되었습니다.";
        };
    }
}
