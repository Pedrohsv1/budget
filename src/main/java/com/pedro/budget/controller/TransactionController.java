package com.pedro.budget.controller;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.TransactionService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionResponse> createTransaction(
            @Validated(OnCreate.class) @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(request, user));
    }

    @GetMapping()
    public ResponseEntity<Page<TransactionResponse>> listTransactions(@AuthenticationPrincipal User user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.listTransactions(user, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        transactionService.deleteTransaction(user, id);
        return ResponseEntity.noContent().build();
    }

}
