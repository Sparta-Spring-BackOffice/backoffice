package com.example.backoffice.product.dto;

import com.example.backoffice.admin.entity.Administrator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetProductResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final Long price;
    private final Long stock;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Administrator admin;

    public GetProductResponse(Long id, String name, String category, Long price, Long stock, String status, LocalDateTime createdAt, LocalDateTime modifiedAt, Administrator admin) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.admin = admin;
    }
}
