package com.pedro.budget.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedro.budget.dto.CategoryRequest;
import com.pedro.budget.dto.CategoryResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest request, User user) {
        return new Category(request.getTitle(), request.getType(), user);
    }

    public CategoryResponse toResponse(Category entity) {
        return new CategoryResponse(entity.getId(), entity.getTitle(), entity.getType());
    }

    public List<CategoryResponse> toResponseList(List<Category> entities) {
        if (entities.isEmpty() || entities == null) {
            return new ArrayList<>();
        }

        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
