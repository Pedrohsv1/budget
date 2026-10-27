package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.CreditPurchase;

public record CreditPurchaseSummaryResponse(
        UUID id,
        Integer totalInstallments,
        CardResponse card) {
    public static CreditPurchaseSummaryResponse from(CreditPurchase creditPurchase) {
        return new CreditPurchaseSummaryResponse(
                creditPurchase.getId(),
                creditPurchase.getTotalInstallments(),
                CardResponse.from(creditPurchase.getCard()));
    }
}
