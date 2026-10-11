package com.pedro.budget.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByUserId(UUID userId);
}
