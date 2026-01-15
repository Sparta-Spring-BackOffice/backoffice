package com.example.backoffice.product.dto;

import com.example.backoffice.product.consts.ProductStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {
    private String name;
    private String category;
    private BigDecimal price;
    private Long stock;
    private ProductStatus status;

}
