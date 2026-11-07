package com.pedro.budget.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedro.budget.dto.CardRequest;
import com.pedro.budget.dto.CardResponse;
import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Card;
import com.pedro.budget.entity.User;
import com.pedro.budget.exception.ResourceNotFoundException;
import com.pedro.budget.repository.BankAccountRepository;
import com.pedro.budget.repository.CardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    public CardResponse createCard(CardRequest request, User user) {
        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.bankAccountId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        Card card = new Card(request.description(), request.cardNumber(), bankAccount, user, request.dueDay(),
                request.closingDay());
        cardRepository.save(card);

        return CardResponse.from(card);
    }

    public CardResponse updateCard(CardRequest request, User user, UUID cardId) {
        Card card = cardRepository.findByIdAndUserId(cardId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        Optional<BankAccount> bankAccount = bankAccountRepository.findByIdAndUserId(request.bankAccountId(),
                user.getId());

        card.update(request.description(), request.cardNumber(), bankAccount.orElse(null), request.dueDay(),
                request.closingDay());
        cardRepository.save(card);

        return CardResponse.from(card);
    }

    public Page<CardResponse> listCards(User user, Pageable pageable) {
        Page<CardResponse> cards = cardRepository.findAllByUserId(user.getId(), pageable).map(CardResponse::from);
        return cards;
    }

    public void deleteCard(UUID cardId, User user) {
        Card card = cardRepository.findByIdAndUserId(cardId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        cardRepository.delete(card);
    }
}
