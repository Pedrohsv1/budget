package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.CategoryRequest;
import com.pedro.budget.dto.CategoryResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.CategoryService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    public final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request, user));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> listCategories(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listCategories(user));
    }

}
