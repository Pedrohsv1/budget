package com.pedro.budget.entity.Transaction;

public enum TransactionType {
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
