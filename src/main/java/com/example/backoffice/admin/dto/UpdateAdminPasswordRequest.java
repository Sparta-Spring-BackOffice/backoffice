package com.example.backoffice.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateAdminPasswordRequest {
    private String currentPassword;
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해야 합니다.")
    private String newPassword;
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해야 합니다.")
    private String newPasswordConfirm;
}
