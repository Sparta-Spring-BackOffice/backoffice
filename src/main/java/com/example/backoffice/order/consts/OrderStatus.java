package com.example.backoffice.order.consts;

import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.order.exception.AlreadyCancelledException;
import com.example.backoffice.order.exception.AlreadyCompletedException;
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

    public OrderStatus next(){
        return switch (this) {
            case READY -> IN_TRANSIT;
            case IN_TRANSIT -> COMPLETED;
            case COMPLETED -> throw new AlreadyCompletedException(ErrorCode.ALREADY_COMPLETED);
            case CANCELLED -> throw new AlreadyCancelledException(ErrorCode.ALREADY_CANCELLED);
        };
    }

}
