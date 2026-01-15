package com.example.backoffice.product.dto;

import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreateProductResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final Long stock;
    private final String status;
    private final LocalDateTime createdAt;

    public CreateProductResponse(Long id, String name, String category, BigDecimal price, Long stock, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;
    }
}
