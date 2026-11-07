package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.CardRequest;
import com.pedro.budget.dto.CardResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.CardService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(request, user));
    }

    @GetMapping
    public ResponseEntity<Page<CardResponse>> listCards(@AuthenticationPrincipal User user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(cardService.listCards(user, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        cardService.deleteCard(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable UUID id, @RequestBody CardRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.updateCard(request, user, id));
    }

}
