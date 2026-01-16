package com.example.backoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class SummaryStatsResponse {
    private final Long totalAdmin;
    private final Long activeAdmin;
    private final Long totalUser;
    private final Long activeUser;
    private final Long totalProduct;
    private final Long lowStockProduct;
    private final Long totalOrder;
    private final Long todayOrder;
    private final Long totalReview;
    private final double averageReview;

    public SummaryStatsResponse(Long totalAdmin, Long activeAdmin, Long totalUser, Long activeUser, Long totalProduct, Long lowStockProduct, Long totalOrder, Long todayOrder, Long totalReview, double averageReview) {
        this.totalAdmin = totalAdmin;
        this.activeAdmin = activeAdmin;
        this.totalUser = totalUser;
        this.activeUser = activeUser;
        this.totalProduct = totalProduct;
        this.lowStockProduct = lowStockProduct;
        this.totalOrder = totalOrder;
        this.todayOrder = todayOrder;
        this.totalReview = totalReview;
        this.averageReview = averageReview;
    }
}
