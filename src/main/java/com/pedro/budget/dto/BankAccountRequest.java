package com.pedro.budget.dto;

import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.dto.validation.OnUpdate;
import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.BankAccountType;
import com.pedro.budget.entity.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record BankAccountRequest(
        @NotNull(message = "Type is required", groups = { OnCreate.class }) BankAccountType type,
        @NotNull(message = "Description is required", groups = {
                OnCreate.class }) @NotBlank(message = "Description cannot be blank", groups = { OnCreate.class,
                        OnUpdate.class }) String description) {
    public BankAccount toEntity(User user) {
        return new BankAccount(type, description, user);
    }
}
