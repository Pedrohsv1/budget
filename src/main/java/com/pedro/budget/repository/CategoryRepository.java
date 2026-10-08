package com.pedro.budget.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByUser(User user);

    Boolean existsByTitleAndUserId(String title, UUID userId);

    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
}
