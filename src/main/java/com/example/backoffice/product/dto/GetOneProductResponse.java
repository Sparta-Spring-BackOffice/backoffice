package com.example.backoffice.product.dto;

import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class GetOneProductResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final Long stock;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String adminName;
    private final String adminEmail;

    public GetOneProductResponse(Long id, String name, String category, BigDecimal price, Long stock, String status, LocalDateTime createdAt, LocalDateTime modifiedAt, String adminName, String adminEmail) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
    }
}
