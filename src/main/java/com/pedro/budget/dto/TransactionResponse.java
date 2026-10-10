package com.pedro.budget.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.Transaction.Installment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransactionResponse {
    private UUID id;

    private Integer amount;

    private String description;

    private LocalDateTime date;

    private Category category;

    private List<Installment> installments;

    private Integer countInstallments;

    String transactionType;
}
