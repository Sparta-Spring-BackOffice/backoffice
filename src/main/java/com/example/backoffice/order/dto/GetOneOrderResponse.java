package com.example.backoffice.order.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class GetOneOrderResponse {

    private final Long id;
    private final String orderNumber;
    private final String userName;
    private final String productName;
    private final BigDecimal price;
    private final Long quantity;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String status;
    private final String adminName;
    private final String adminEmail;
    private final String adminRole;

    public GetOneOrderResponse(Long id, String orderNumber, String userName, String productName, BigDecimal price, Long quantity, BigDecimal amount, LocalDateTime createdAt, LocalDateTime modifiedAt, String status, String adminName, String adminEmail, String adminRole) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
    }
}

