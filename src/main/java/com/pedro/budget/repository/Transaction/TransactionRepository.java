package com.pedro.budget.repository.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByUserId(UUID userId);

    Page<Transaction> findAllByUserId(UUID userId, Pageable pageable);

    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);
}
