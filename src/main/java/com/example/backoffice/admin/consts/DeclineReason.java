package com.example.backoffice.admin.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum DeclineReason {
    UNAUTHORIZED,
    ADMIN_EXCESS,
    TIME_OUT
}
