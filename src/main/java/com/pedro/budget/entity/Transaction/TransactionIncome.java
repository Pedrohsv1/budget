package com.pedro.budget.entity.Transaction;

import java.time.LocalDate;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;
import com.pedro.budget.enums.IncomeType;
import com.pedro.budget.enums.TransactionType;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("INCOME")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionIncome extends Transaction {
    @NotNull
    @Enumerated(EnumType.STRING)
    IncomeType incomeType;

    public TransactionIncome(Long amount, String description, LocalDate date,
            User user, Category category, IncomeType type, BankAccount bankAccount, Person person) {
        this.incomeType = type;
        super(amount, description, date, user, category, TransactionType.INCOME, bankAccount, person);
    }
}
