package com.pedro.budget.enums;

public enum IncomeType {
    PIX("Pix"),
    INVESTMENTS("Investments"),
    TED("TED");

    private final String type;

    IncomeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
