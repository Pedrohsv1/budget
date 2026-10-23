package com.pedro.budget.enums;

public enum ExpenseType {
    PIX("Pix"),
    BOLETO("Boleto"),
    TED("TED");

    private final String type;

    ExpenseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
