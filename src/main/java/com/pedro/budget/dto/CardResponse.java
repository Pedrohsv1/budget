package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.entity.Card;

public record CardResponse(
        UUID id,
        String description,
        String cardNumber) {
    public static CardResponse from(Card card) {
        return new CardResponse(card.getId(), card.getDescription(), card.getCardNumber());
    }
}
