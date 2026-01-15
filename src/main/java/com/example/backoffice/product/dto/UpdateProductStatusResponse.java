package com.example.backoffice.product.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateProductStatusResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final Long stock;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public UpdateProductStatusResponse(Long id, String name, String category, Long stock, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;

    }
}
