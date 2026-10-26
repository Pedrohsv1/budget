package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.BankAccountType;

public record BankAccountReponse(
        UUID id,
        BankAccountType type,
        String description) {
    public static BankAccountReponse from(BankAccount bankAccount) {
        return new BankAccountReponse(
                bankAccount.getId(),
                bankAccount.getType(),
                bankAccount.getDescription());
    }
}
