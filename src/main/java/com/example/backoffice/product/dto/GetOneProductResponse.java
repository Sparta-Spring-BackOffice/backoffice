package com.example.backoffice.product.dto;

import com.example.backoffice.review.dto.LatestReviewDto;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetOneProductResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final Long stock;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String adminName;
    private final String adminEmail;
    private final Long totalCount;
    private final Double avgRating;
    private final Long star1;
    private final Long star2;
    private final Long star3;
    private final Long star4;
    private final Long star5;
    List<LatestReviewDto> latestReviews;

    public GetOneProductResponse(Long id, String name, String category, BigDecimal price, Long stock, String status, LocalDateTime createdAt, LocalDateTime modifiedAt, String adminName, String adminEmail, Long totalCount, Double avgRating, Long star1, Long star2, Long star3, Long star4, Long star5, List<LatestReviewDto> latestReviews) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.totalCount = totalCount;
        this.avgRating = avgRating;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
        this.latestReviews = latestReviews;
    }
}
