package com.example.backoffice.order.dto;

import lombok.Getter;

@Getter
public class ChangedStatusOrderResponse {

    private final String orderStatus;

    public ChangedStatusOrderResponse(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
