package com.example.backoffice.order.consts;

import lombok.Getter;

@Getter
public enum OrderStatus {
    READY("READY"),
    IN_TRANSIT("IN_TRANSIT"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

}
