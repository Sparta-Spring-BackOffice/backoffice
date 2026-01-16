package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminRoleRequest {
    @NotNull(message = "역할 변경에 실패했습니다. 변경할 역할을 선택하여 주십시오.")
    private AdminRole role;
}
