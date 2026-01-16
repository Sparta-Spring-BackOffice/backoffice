package com.example.backoffice.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
    @NotNull(message = "고객 정보는 필수 입력 항목입니다.")
    private Long userId;

    @NotNull(message = "상품 정보는 필수 입력 항목입니다.")
    private Long productId;

    @NotNull(message = "주문 수량은 필수 입력 항목입니다.")
    @Positive
    @Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
    private Long quantity;
}
