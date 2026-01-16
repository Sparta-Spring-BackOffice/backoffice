package com.example.backoffice.order.dto;

import lombok.Getter;

@Getter
public class ChangedOrderStatusResponse {

    private final String orderStatus;

    public ChangedOrderStatusResponse(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
