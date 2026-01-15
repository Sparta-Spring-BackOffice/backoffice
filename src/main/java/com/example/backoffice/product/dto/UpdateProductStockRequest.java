package com.example.backoffice.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateProductStockRequest {

    @NotNull(message = "재고 수량은 필수 입력 항목입니다.")
    private Long stock;

}
