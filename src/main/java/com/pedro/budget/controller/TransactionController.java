package com.pedro.budget.controller;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.TransactionService;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public TransactionResponse createTransaction(@Validated(OnCreate.class) @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user) {
        return transactionService.createTransaction(request, user);
    }

    @GetMapping("/")
    public List<TransactionResponse> listTransactions(@AuthenticationPrincipal User user) {
        return transactionService.listTransactions(user);
    }

}
