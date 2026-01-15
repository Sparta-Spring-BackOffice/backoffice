package com.example.backoffice.order.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class GetOrderResponse {

    private final Long id;
    private final String orderNumber;
    private final String userName;
    private final String productName;
    private final BigDecimal price;
    private final Long stock;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String status;
    private final String adminName;


    public GetOrderResponse(Long id, String orderNumber, String userName, String productName, BigDecimal price, Long stock, LocalDateTime createdAt, LocalDateTime modifiedAt, String status, String adminName) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.adminName = adminName;
    }
}
