package com.pedro.budget.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Card;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByIdAndUserId(UUID id, UUID userId);

    Page<Card> findAllByUserId(UUID id, Pageable pageable);
}
