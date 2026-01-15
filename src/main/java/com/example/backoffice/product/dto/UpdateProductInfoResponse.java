package com.example.backoffice.product.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class UpdateProductInfoResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public UpdateProductInfoResponse(Long id, String name, String category, BigDecimal price, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;

    }
}
