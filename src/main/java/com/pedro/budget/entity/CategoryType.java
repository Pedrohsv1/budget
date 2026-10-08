package com.pedro.budget.entity;

public enum CategoryType {
    FOOD("Food"),
    SHOPPING("Shopping"),
    HEALTH_AND_CARE("Health and care"),
    DIGITAL_SERVICES("Digital services"),
    TRANSPORT("Transport"),
    HOUSING("Housing"),
    LEISURE_AND_ENTERTAINMENT("Leisure and entertainment"),
    FINANCES("Finances"),
    INCOME("Income"),
    EDUCATION("Education"),
    TAXES_AND_FEES("Taxes and fees"),
    INSURANCE("Insurance"),
    BETTING_AND_GAMES("Betting and games"),
    DONATIONS("Donations"),
    OTHERS("Others");

    private final String name;

    CategoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
