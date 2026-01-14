package com.example.backoffice.product.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {
    private String name;
    private String category;
    private BigDecimal price;
    private Long stock;
    private String status;

}
