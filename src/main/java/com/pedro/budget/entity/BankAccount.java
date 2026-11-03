package com.pedro.budget.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    private String description;

    private Long initialBalance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    public BankAccount(BankAccountType type, String description, User user, Long initialBalance) {
        this.type = type;
        this.description = description;
        this.user = user;
        this.initialBalance = initialBalance;
    }

    public void update(BankAccountType type, String description, Long initialBalance) {
        if (type != null) {
            this.type = type;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        if (initialBalance != null) {
            this.initialBalance = initialBalance;
        }
    }
}
