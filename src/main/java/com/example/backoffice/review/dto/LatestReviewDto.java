package com.example.backoffice.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LatestReviewDto {
    private final String userName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public LatestReviewDto(String userName, Integer rating, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userName = userName;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
