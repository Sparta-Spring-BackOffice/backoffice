package com.example.backoffice.user.dto;

import com.example.backoffice.user.consts.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserStatusRequest {

    @NotBlank(message = "상태 값은 필수 입력 항목입니다.")
    private UserStatus status;
}
