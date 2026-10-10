package com.pedro.budget.entity.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.budget.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "installements")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private Integer number;

    private Integer amountCents;

    private LocalDateTime dateTime;

    private LocalDateTime paidAt;
}
