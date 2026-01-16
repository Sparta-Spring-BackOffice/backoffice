package com.example.backoffice.order.dto;

import lombok.Getter;

@Getter
public class ChangedStatusOrderResponse {

    private final String oderStatus;

    public ChangedStatusOrderResponse(String oderStatus) {
        this.oderStatus = oderStatus;
    }
}
