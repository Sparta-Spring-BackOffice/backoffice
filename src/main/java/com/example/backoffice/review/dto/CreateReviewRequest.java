package com.example.backoffice.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class CreateReviewRequest {
    @Min(value = 1,message = "평점은 1 이상이여야 합니다.")
    @Max(value = 5, message = "평점은 5 이상이여야 합니다.")
    private Integer rating;
    private String content;
}
