package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.BankAccountType;

public record BankAccountResponse(
        UUID id,
        BankAccountType type,
        String description) {
    public static BankAccountResponse from(BankAccount bankAccount) {
        return new BankAccountResponse(
                bankAccount.getId(),
                bankAccount.getType(),
                bankAccount.getDescription());
    }
}
