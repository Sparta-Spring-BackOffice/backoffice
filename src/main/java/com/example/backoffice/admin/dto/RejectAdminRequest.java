package com.example.backoffice.admin.dto;

import com.example.backoffice.admin.consts.DeclineReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RejectAdminRequest {
    @NotNull(message = "거절 사유를 입력해 주세요")
    private DeclineReason declineReason;
}
