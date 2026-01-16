package com.example.backoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class ReviewRatingDto {
    private final Integer rating;
    private final Long count;

    public ReviewRatingDto(Integer rating, Long count) {
        this.rating = rating;
        this.count = count;
    }
}
