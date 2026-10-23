package com.pedro.budget.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pedro.budget.entity.Transaction.TransactionCredit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit-purchase")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreditPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Card card;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("installmentNumber ASC")
    private List<TransactionCredit> transactions = new ArrayList<>();

    @NotNull(message = "Total installments is required")
    private Integer totalInstallments;

    public CreditPurchase(Card card, Integer totalInstallments) {
        this.card = card;
        this.totalInstallments = totalInstallments;
    }

    public void addTransaction(TransactionCredit transaction) {
        transactions.add(transaction);
        transaction.setPurchase(this);
    }
}
