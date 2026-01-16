package com.example.backoffice.dashboard.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class WidgetsStatsResponse {
    private final BigDecimal totalSales;
    private final BigDecimal todaySales;
    private final Long orderReady;
    private final Long orderInTransit;
    private final Long orderCompleted;
    private final Long lowStockProduct;
    private final Long soldOutProduct;

    public WidgetsStatsResponse(BigDecimal totalSales, BigDecimal todaySales, Long orderReady, Long orderInTransit, Long orderCompleted, Long lowStockProduct, Long soldOutProduct) {
        this.totalSales = totalSales;
        this.todaySales = todaySales;
        this.orderReady = orderReady;
        this.orderInTransit = orderInTransit;
        this.orderCompleted = orderCompleted;
        this.lowStockProduct = lowStockProduct;
        this.soldOutProduct = soldOutProduct;
    }
}
