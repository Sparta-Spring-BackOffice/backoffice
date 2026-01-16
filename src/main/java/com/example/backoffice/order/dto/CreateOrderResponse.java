package com.example.backoffice.order.dto;

import com.example.backoffice.order.consts.OrderStatus;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreateOrderResponse {
    private final Long id;
    private final LocalDateTime createdAt;
    private final String orderNumber;
    private final OrderStatus status;
    private final BigDecimal amount;
    private final Long userId;
    private final Long adminId;

    public CreateOrderResponse(Long id, LocalDateTime createdAt, String orderNumber, OrderStatus status, BigDecimal amount, Long userId, Long adminId) {
        this.id = id;
        this.createdAt = createdAt;
        this.orderNumber = orderNumber;
        this.status = status;
        this.amount = amount;
        this.userId = userId;
        this.adminId = adminId;
    }
}