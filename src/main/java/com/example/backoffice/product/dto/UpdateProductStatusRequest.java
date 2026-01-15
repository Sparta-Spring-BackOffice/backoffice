package com.example.backoffice.product.dto;

import com.example.backoffice.product.consts.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateProductStatusRequest {

    @NotNull(message = "판매 상태는 필수 입력 항목입니다.")
    private ProductStatus status;

}
