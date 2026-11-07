package com.pedro.budget.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private String cardNumber; // Four digits of the card number

    private Integer dueDay;

    private Integer closingDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Card(String description, String cardNumber, BankAccount bankAccount, User user, Integer dueDay,
            Integer closingDay) {
        this.description = description;
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
        this.user = user;
        this.dueDay = dueDay;
        this.closingDay = closingDay;
    }

    public void update(String description, String cardNumber, BankAccount bankAccount, Integer dueDay,
            Integer closingDay) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        if (cardNumber != null && !cardNumber.isBlank()) {
            this.cardNumber = cardNumber;
        }
        if (bankAccount != null) {
            this.bankAccount = bankAccount;
        }
        if (dueDay != null) {
            this.dueDay = dueDay;
        }
        if (closingDay != null) {
            this.closingDay = closingDay;
        }
    }
}
