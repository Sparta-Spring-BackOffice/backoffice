package com.example.backoffice.review.dto;

import lombok.Getter;

@Getter
public class ProductReviewStatsDto {
    private final Long productId;
    private final Long totalCount;
    private final Double avgRating;
    private final Long star1;
    private final Long star2;
    private final Long star3;
    private final Long star4;
    private final Long star5;

    public ProductReviewStatsDto(Long productId, Long totalCount, Double avgRating, Long star1, Long star2, Long star3, Long star4, Long star5) {
        this.productId = productId;
        this.totalCount = totalCount;
        this.avgRating = avgRating;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
    }
}
