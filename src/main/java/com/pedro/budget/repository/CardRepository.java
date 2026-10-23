package com.pedro.budget.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Card;

public interface CardRepository extends JpaRepository<Card, UUID> {

}
