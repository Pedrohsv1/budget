package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.CreditPurchase;

public record CreditPurchaseResponse(
        UUID id,
        Integer totalInstallments,
        CardResponse card) {
    public static CreditPurchaseResponse from(CreditPurchase creditPurchase) {
        return new CreditPurchaseResponse(
                creditPurchase.getId(),
                creditPurchase.getTotalInstallments(),
                new CardResponse(
                        creditPurchase.getCard().getId(),
                        creditPurchase.getCard().getDescription(),
                        creditPurchase.getCard().getCardNumber()));
    }
}
