package com.example.backoffice.dashboard.dto;

import com.example.backoffice.order.consts.OrderStatus;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class LateOrderListResponse {
    private final String orderNumber;
    private final String userName;
    private final String productName;
    private final BigDecimal amount;
    private final OrderStatus status;

    public LateOrderListResponse(String orderNumber, String userName, String productName, BigDecimal amount, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.productName = productName;
        this.amount = amount;
        this.status = status;
    }
}
