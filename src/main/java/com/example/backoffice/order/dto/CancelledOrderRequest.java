package com.example.backoffice.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CancelledOrderRequest {

    @NotBlank(message = "취소 사유를 반드시 입력해야 합니다.")
    private String reason;
}
