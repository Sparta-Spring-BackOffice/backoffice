package com.example.backoffice.order.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserOrderDto {
    private final Long userID;
    private final Long totalOrderNum;
    private final BigDecimal totalOrderPrice;

    public UserOrderDto(Long userID, Long totalOrderNum, BigDecimal totalOrderPrice) {
        this.userID = userID;
        this.totalOrderNum = totalOrderNum;
        this.totalOrderPrice = totalOrderPrice;
    }
}
