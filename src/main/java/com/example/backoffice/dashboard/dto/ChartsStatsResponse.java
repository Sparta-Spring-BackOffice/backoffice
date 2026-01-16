package com.example.backoffice.dashboard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChartsStatsResponse {
    private final List<ReviewRatingDto> countByReviewRating;
    private final List<UserStatusDto> countByUserStatus;
    private final List<CategoryDto> countByCategory;


    public ChartsStatsResponse(List<ReviewRatingDto> countByReviewRating, List<UserStatusDto> countByUserStatus, List<CategoryDto> countByCategory) {
        this.countByReviewRating = countByReviewRating;
        this.countByUserStatus = countByUserStatus;
        this.countByCategory = countByCategory;
    }
}

