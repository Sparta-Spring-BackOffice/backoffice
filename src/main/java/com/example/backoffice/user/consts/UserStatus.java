package com.example.backoffice.user.consts;

import lombok.Getter;

public enum UserStatus {

    // 서로 같은 값으로 수정
    ACTIVE("ACTIVE"),
    NON_ACTIVE("NON_ACTIVE"),
    SUSPEND("SUSPEND");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getStatus() {
        return description;
    }

}
