package com.example.backoffice.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneReviewResponse {
    private final Long id;
    private final String productName;
    private final String userName;
    private final String userEmail;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneReviewResponse(Long id, String productName, String userName, String userEmail, Integer rating, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.productName = productName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
