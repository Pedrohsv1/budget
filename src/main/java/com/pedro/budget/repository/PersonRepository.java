package com.pedro.budget.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByNameAndUserId(String name, UUID userId);

    Page<Person> findByUserId(UUID userId, Pageable pageable);

    Optional<Person> findByIdAndUserId(UUID id, UUID userId);

    @Query("""
        select p from Person p
            where p.user.id = :userId
                and (lower(p.name) like lower(concat('%', :q, '%'))
                    or (lower(p.email) like lower(concat('%', :q, '%'))))
    """)
    List<Person> search(@Param("userId") UUID userId, @Param("q") String q);
}
