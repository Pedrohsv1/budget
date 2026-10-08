package com.pedro.budget.dto;

import com.pedro.budget.entity.CategoryType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Value;

@Value
public class CategoryRequest {
    @NotEmpty(message = "Title cannot be blank")
    String title;

    @NotNull(message = "Type cannot be null")
    CategoryType type;
}
