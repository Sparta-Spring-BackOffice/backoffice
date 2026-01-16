package com.example.backoffice.dashboard.dto;

import lombok.Getter;

@Getter
public class CategoryDto {
    private final String category;
    private final Long count;

    public CategoryDto(String category, Long count) {
        this.category = category;
        this.count = count;
    }
}
