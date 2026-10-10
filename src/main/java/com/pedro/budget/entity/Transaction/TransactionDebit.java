package com.pedro.budget.entity.Transaction;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DEBIT")
public class TransactionDebit extends Transaction {

}
