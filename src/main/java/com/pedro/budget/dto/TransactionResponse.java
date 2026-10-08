package com.pedro.budget.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.pedro.budget.entity.Category;

import lombok.Value;

@Value
public class TransactionResponse {
    private UUID id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime date;

    private Category category;
}
