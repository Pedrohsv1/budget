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
        CategoryResponse category,
        IncomeType incomeType,
        ExpenseType expenseType,
        CreditPurchaseResponse creditPurchase,
        BankAccountResponse bankAccount,
        TransactionType type) {
    public static TransactionResponse from(Transaction t) {
        TransactionResponse.TransactionResponseBuilder builder = TransactionResponse.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .description(t.getDescription())
                .date(t.getDate())
                .category(CategoryResponse.from(t.getCategory()))
                .type(t.getTransactionType())
                .bankAccount(BankAccountResponse.from(t.getBankAccount()));

        return switch (t) {
            case TransactionIncome income -> builder
                    .incomeType(income.getIncomeType())
                    .build();

            case TransactionExpense expense -> builder
                    .expenseType(expense.getExpenseType())
                    .build();

            case TransactionCredit credit -> builder
                    .creditPurchase(CreditPurchaseResponse.from(credit.getPurchase()))
                    .build();

            default -> builder.build();
        };
    }
}