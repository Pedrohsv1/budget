package com.pedro.budget.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.CreditPurchase;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionExpense;
import com.pedro.budget.entity.Transaction.TransactionIncome;
import com.pedro.budget.enums.ExpenseType;
import com.pedro.budget.enums.IncomeType;
import com.pedro.budget.enums.TransactionType;

import lombok.Builder;

@Builder
public record TransactionResponse(
        UUID id,
        Long amount,
        String description,
        LocalDate date,
        Category category,
        IncomeType incomeType,
        ExpenseType expenseType,
        CreditPurchase creditPurchase,
        BankAccount bankAccount,
        TransactionType type) {
    public static TransactionResponse from(Transaction t) {
        TransactionResponse.TransactionResponseBuilder builder = TransactionResponse.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .description(t.getDescription())
                .date(t.getDate())
                .category(t.getCategory())
                .type(t.getTransactionType())
                .bankAccount(t.getBankAccount());

        return switch (t) {
            case TransactionIncome income -> builder
                    .incomeType(income.getIncomeType())
                    .build();

            case TransactionExpense expense -> builder
                    .expenseType(expense.getExpenseType())
                    .build();

            case TransactionCredit credit -> builder
                    .creditPurchase(credit.getPurchase())
                    .build();

            default -> builder.build();
        };
    }
}