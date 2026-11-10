package com.pedro.budget.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByIdAndUserId(UUID id, UUID userId);

    Page<Card> findAllByUserId(UUID id, Pageable pageable);

    @Query("""
    select c from Card c
        where c.user.id = :userId
            and (lower(c.description) like lower(concat('%', :q ,'%'))
                or (c.cardNumber like lower(concat('%', :q ,'%'))))
    """)
    List<Card> search(@Param("userId") UUID userId, @Param("q") String q);
}
