package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.CategoryType;

import lombok.Value;

@Value
public class CategoryResponse {
    UUID id;

    String title;

    CategoryType type;
}
