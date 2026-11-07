package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.Card;

public record CardResponse(
        UUID id,
        String description,
        String cardNumber,
        Integer dueDay,
        Integer closingDay,
        BankAccountResponse bankAccount) {
    public static CardResponse from(Card card) {
        return new CardResponse(
                card.getId(),
                card.getDescription(),
                card.getCardNumber(),
                card.getDueDay(),
                card.getClosingDay(),
                card.getBankAccount() != null ? BankAccountResponse.from(card.getBankAccount()) : null);
    }
}

