package com.pedro.budget.enums;

/**
 * TransactionType
 */
public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense"),
    CREDIT_EXPENSE("Credit Expense");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
