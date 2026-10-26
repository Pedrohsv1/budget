package com.pedro.budget.entity.Transaction;

import java.time.LocalDate;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;
import com.pedro.budget.enums.ExpenseType;
import com.pedro.budget.enums.TransactionType;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("EXPENSE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionExpense extends Transaction {
    ExpenseType expenseType;

    public TransactionExpense(Long amount, String description, LocalDate date,
            User user, Category category, ExpenseType type, BankAccount bankAccount) {
        this.expenseType = type;
        super(amount, description, date, user, category, TransactionType.EXPENSE, bankAccount);
    }
}
