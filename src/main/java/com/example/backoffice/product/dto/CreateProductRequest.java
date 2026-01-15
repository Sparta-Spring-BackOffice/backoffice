package com.example.backoffice.product.dto;

import com.example.backoffice.product.consts.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class CreateProductRequest {

    @NotBlank(message = "상품 이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

    @Positive
    @NotNull(message = "가격은 필수 입력 항목입니다.")
    private BigDecimal price;

    @NotNull(message = "재고 수량은 필수 입력 항목입니다.")
    private Long stock;

    @NotNull(message = "판매 상태는 필수 입력 항목입니다.")
    private ProductStatus status;

}
