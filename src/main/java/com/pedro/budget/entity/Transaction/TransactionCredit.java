package com.pedro.budget.entity.Transaction;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CREDIT")
@Getter
@Setter
public class TransactionCredit extends Transaction {
    private Integer countInstallments;

    @OneToMany(mappedBy = "transaction", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1, message = "Installmente should be one or more")
    private List<Installment> installments;
}
