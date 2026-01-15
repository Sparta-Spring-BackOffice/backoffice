package com.example.backoffice.product.consts;

public enum ProductStatus {
    FOR_SALE("FOR_SALE"),
    SOLD_OUT("SOLD_OUT"),
    DISCONTINUED("DISCONTINUED");

    private final String statusName;

    ProductStatus(String statusName) {
        this.statusName = statusName;
    }
    public String getStatusName() {
        return statusName;
    }
}
