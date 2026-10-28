package com.pedro.budget.entity.Transaction;

import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.CreditPurchase;
import com.pedro.budget.enums.TransactionType;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CREDIT_EXPENSE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionCredit extends Transaction {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private CreditPurchase purchase;

    private Integer installmentNumber;
    private Integer installmentTotal;

    public TransactionCredit(Long amount, String description, java.time.LocalDate date,
            com.pedro.budget.entity.User user, com.pedro.budget.entity.Category category,
            CreditPurchase purchase, Integer installmentNumber, Integer installmentTotal, BankAccount bankAccount) {
        super(amount, description, date, user, category, TransactionType.CREDIT_EXPENSE, bankAccount);
        this.purchase = purchase;
        this.installmentNumber = installmentNumber;
        this.installmentTotal = installmentTotal;
    }

    public void setPurchase(CreditPurchase creditPurchase) {
        this.purchase = creditPurchase;
    }
}
