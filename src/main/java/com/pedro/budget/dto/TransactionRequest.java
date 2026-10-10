package com.pedro.budget.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Value;

import com.pedro.budget.dto.validation.*;
import com.pedro.budget.entity.Transaction.TransactionType;

@Value
public class TransactionRequest {
    @NotNull(message = "Amount cannot be null", groups = { OnCreate.class })
    @Min(value = 0, message = "Amount cannot be less than zero")
    Integer amount;

    @NotEmpty(message = "Description cannot be empty", groups = { OnCreate.class })
    String description;

    @NotNull(message = "Date cannot be null", groups = { OnCreate.class })
    @Past(message = "Date must be in the past", groups = { OnCreate.class })
    LocalDateTime date;

    @NotNull(message = "Date cannot be null", groups = { OnCreate.class })
    UUID categoryId;

    @NotNull(message = "Type cannot be null", groups = { OnCreate.class })
    TransactionType type;

    Integer installements;
}
