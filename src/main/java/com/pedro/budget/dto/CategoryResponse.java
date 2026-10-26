package com.pedro.budget.dto;

import java.util.List;
import java.util.UUID;

import com.pedro.budget.entity.Category;
import com.pedro.budget.enums.CategoryType;

public record CategoryResponse(
        UUID id,
        String title,
        CategoryType type) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getTitle(), category.getType());
    }

    public static List<CategoryResponse> fromList(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getTitle(),
                        category.getType()))
                .toList();
    }
}
