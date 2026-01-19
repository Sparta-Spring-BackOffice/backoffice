package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class updateAdminStatusRequest {
    @NotNull(message = "변경할 상태를 선택하여 주십시오.")
    private AdminStatus status;
}
