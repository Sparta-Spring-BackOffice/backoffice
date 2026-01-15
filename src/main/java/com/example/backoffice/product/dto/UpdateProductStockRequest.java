package com.example.backoffice.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateProductStockRequest {

    @NotBlank(message = "재고 수량은 필수 입력 항목입니다.")
    private Long stock;

}
