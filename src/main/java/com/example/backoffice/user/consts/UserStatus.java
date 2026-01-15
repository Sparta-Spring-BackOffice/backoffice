package com.example.backoffice.user.consts;


public enum UserStatus {

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
