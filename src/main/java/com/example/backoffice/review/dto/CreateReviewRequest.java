package com.example.backoffice.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1,message = "평점은 1 이상이여야 합니다.")
    @Max(value = 5, message = "평점은 5 이하이여야 합니다.")
    private Integer rating;

    private String content;
}
