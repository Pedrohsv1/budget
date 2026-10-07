package com.pedro.budget.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "transactions")
@Table(name = "transactions")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Amount cannot be null")
    @DecimalMax(value = "10000000.00", message = "Amount cannot be greater than 10,000,000")
    @DecimalMin(value = "0.01", message = "Amount cannot be lower than 0")
    private BigDecimal amount;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Date cannot be null")
    @Past()
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    User user;

    // Function for creating a new transaction

    public Transaction(BigDecimal amount, String description, LocalDateTime date, User user) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
    }
}
