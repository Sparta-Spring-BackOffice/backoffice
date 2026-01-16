package com.example.backoffice.dashboard.dto;

import com.example.backoffice.user.consts.UserStatus;
import lombok.Getter;

@Getter
public class UserStatusDto {
    private final UserStatus status;
    private final Long count;

    public UserStatusDto(UserStatus status, Long count) {
        this.status = status;
        this.count = count;
    }
}
