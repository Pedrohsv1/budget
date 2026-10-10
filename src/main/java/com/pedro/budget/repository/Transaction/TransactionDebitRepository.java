package com.pedro.budget.repository.Transaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Transaction.TransactionDebit;

public interface TransactionDebitRepository extends JpaRepository<TransactionDebit, UUID> {

}
