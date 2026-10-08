package com.pedro.budget.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.CategoryRequest;
import com.pedro.budget.dto.CategoryResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.CategoryType;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.CategoryMapper;
import com.pedro.budget.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request, User user) {
        if (categoryRepository.existsByTitleAndUserId(request.getTitle(), user.getId())) {
            throw new IllegalArgumentException("Category title must be unique");
        }

        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request, user)));
    }

    public void deleteCategory(UUID id, User user) {
        Category category = categoryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category"));

        categoryRepository.delete(category);
    }

    public List<CategoryResponse> listCategories(User user) {
        List<Category> categories = categoryRepository.findAllByUser(user);

        return categoryMapper.toResponseList(categories);
    }
}