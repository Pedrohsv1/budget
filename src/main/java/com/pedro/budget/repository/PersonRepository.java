package com.pedro.budget.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByNameAndUserId(String name, UUID userId);

    Page<Person> findByUserId(UUID userId, Pageable pageable);

    Optional<Person> findByIdAndUserId(UUID id, UUID userId);
}
