package com.pedro.budget.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIdAndUserId(UUID bankId, UUID userId);

    Page<BankAccount> findAllByUserId(UUID userId, Pageable pageable);
}
