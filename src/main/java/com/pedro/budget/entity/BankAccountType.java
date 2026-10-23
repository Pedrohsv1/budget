package com.pedro.budget.entity;

public enum BankAccountType {

    // --- Major Retail Traditional Banks ---
    ITAU("Itaú Unibanco"),
    BANCO_DO_BRASIL("Banco do Brasil"),
    BRADESCO("Banco Bradesco"),
    CAIXA("Caixa Econômica Federal"),
    SANTANDER("Santander Brasil"),

    // --- High-Growth Digital Banks & Fintechs ---
    NUBANK("Nubank"),
    INTER("Banco Inter"),
    C6_BANK("C6 Bank"),
    PICPAY("PicPay"),
    MERCADO_PAGO("Mercado Pago"),
    PAGBANK("PagBank"),
    NEON("Neon"),
    NEXT("Banco Next"),

    // --- Investment & Specialized Consumer Banks ---
    BTG_PACTUAL("BTG Pactual"),
    XP_INVESTIMENTOS("XP Investimentos"),
    BANCO_PAN("Banco Pan"),
    BANCO_SAFRA("Banco Safra"),
    BANCO_BV("Banco BV"),

    // --- Major Credit Cooperatives ---
    SICOOB("Sicoob"),
    SICREDI("Sicredi"),

    // --- Fallback ---
    OUTROS("Outros");

    private final String displayName;

    BankAccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}