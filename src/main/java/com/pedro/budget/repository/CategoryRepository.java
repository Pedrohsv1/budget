package com.pedro.budget.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByUserId(UUID userId);

    Boolean existsByTitleIgnoreCaseAndUserId(String title, UUID userId);

    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
}
