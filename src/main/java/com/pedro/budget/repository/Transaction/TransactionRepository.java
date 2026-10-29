package com.pedro.budget.repository.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByUserId(UUID userId);

    @EntityGraph(attributePaths = { "category", "bankAccount" }) // Create a joined fetch query to load the category and
                                                                 // bankAccount all at once ( in a only query)
    Page<Transaction> findAllByUserId(UUID userId, Pageable pageable);

    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);
}
