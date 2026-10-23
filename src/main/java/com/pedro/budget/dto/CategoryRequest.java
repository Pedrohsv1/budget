package com.pedro.budget.dto;

import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;
import com.pedro.budget.enums.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Title cannot be null") @NotBlank(message = "Title cannot be blank") String title,
        @NotNull(message = "Type cannot be null") CategoryType type) {
    public Category toEntity(User user) {
        return new Category(title, type, user);
    }
}
