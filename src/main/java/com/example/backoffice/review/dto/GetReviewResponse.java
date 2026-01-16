package com.example.backoffice.review.dto;

import com.example.backoffice.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReviewResponse {
    private final Long id;
    private final String orderNumber;
    private final String userName;
    private final String productName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetReviewResponse(Long id, String orderNumber,  String userName, String productName, Integer rating, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.productName = productName;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
