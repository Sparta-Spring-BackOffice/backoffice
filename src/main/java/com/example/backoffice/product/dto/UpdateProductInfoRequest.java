package com.example.backoffice.product.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateProductInfoRequest {
    private String name;
    private String category;
    private BigDecimal price;

}
