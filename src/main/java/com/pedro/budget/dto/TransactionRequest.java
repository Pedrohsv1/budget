package com.pedro.budget.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Value;

import com.pedro.budget.dto.validation.*;

@Value
public class TransactionRequest {
    @NotNull(message = "Amount cannot be null", groups = { OnCreate.class })
    @DecimalMax(value = "10000000.00", message = "Amount cannot be greater than 10,000,000", groups = { OnCreate.class,
            OnUpdate.class })
    @DecimalMin(value = "0.01", message = "Amount cannot be lower than 0", groups = { OnCreate.class, OnUpdate.class })
    BigDecimal amount;

    @NotEmpty(message = "Description cannot be empty", groups = { OnCreate.class })
    String description;

    @NotNull(message = "Date cannot be null", groups = { OnCreate.class })
    @Past(message = "Date must be in the past", groups = { OnCreate.class, OnUpdate.class })
    LocalDateTime date;

    @NotNull(message = "Date cannot be null", groups = { OnCreate.class })
    UUID categoryId;
}
