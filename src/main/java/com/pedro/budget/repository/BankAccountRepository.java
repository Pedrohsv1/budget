package com.pedro.budget.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

}
