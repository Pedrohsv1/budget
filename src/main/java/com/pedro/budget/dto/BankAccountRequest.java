package com.pedro.budget.dto;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.BankAccountType;
import com.pedro.budget.entity.User;

import jakarta.validation.constraints.NotNull;

public record BankAccountRequest(
        @NotNull(message = "Type is required") BankAccountType type,
        @NotNull(message = "Description is required") String description) {
    public BankAccount toEntity(User user) {
        return new BankAccount(type, description, user);
    }
}
