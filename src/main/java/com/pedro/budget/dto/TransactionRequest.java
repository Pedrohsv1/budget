package com.pedro.budget.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.CreditPurchase;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionExpense;
import com.pedro.budget.entity.Transaction.TransactionIncome;
import com.pedro.budget.enums.ExpenseType;
import com.pedro.budget.enums.IncomeType;
import com.pedro.budget.enums.TransactionType;

public record TransactionRequest(
        @NotNull(message = "Amount cannot be null") @Min(value = 1, message = "Amount must be greater than 0") Long amount,
        @NotEmpty(message = "Description cannot be empty") String description,
        @NotNull(message = "Date cannot be null") LocalDate date,
        @NotNull(message = "Category cannot be null") UUID categoryId,
        @NotNull(message = "Transaction type cannot be null") TransactionType type,
        @NotNull(message = "Bank account cannot be null") UUID bankAccountId,

        // Can be used for income and expense transactions
        IncomeType incomeType,
        ExpenseType expenseType,
        // Can be used for credit transactions
        Integer installmentNumber,
        Integer installmentTotal,
        UUID cardId) {
    public Transaction from(BankAccount bankAccount, User user, CreditPurchase creditPurchase, Category category) {
        return switch (type) {
            case INCOME -> new TransactionIncome(amount, description, date, user, category, incomeType, bankAccount);
            case EXPENSE -> new TransactionExpense(amount, description, date, user, category, expenseType, bankAccount);
            case CREDIT_EXPENSE -> new TransactionCredit(amount, description, date, user, category, creditPurchase,
                    installmentNumber, installmentTotal,
                    bankAccount);
        };
    }
}
