package com.pedro.budget.entity.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "transactions")
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "type", insertable = false, updatable = false)
    private String transactionType;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount cannot be less than zero")
    private Integer amount;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Date cannot be null")
    @Past()
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull
    Category category;
}
