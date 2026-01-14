package com.example.backoffice.user.consts;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("활성"),
    NON_ACTIVE("비활성"),
    SUSPEND("정지");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

}
