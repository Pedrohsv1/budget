package com.pedro.budget.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.BankAccountResponse;
import com.pedro.budget.dto.BankAccountRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.BankAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bank-accounts")
@AllArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping()
    public ResponseEntity<BankAccountResponse> createBankAccount(@RequestBody BankAccountRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.createBankAccount(request, user));
    }

    @GetMapping("/search")
    public Page<BankAccountResponse> listBankAccounts(@AuthenticationPrincipal User user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return bankAccountService.listBankAccounts(user, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponse> updateBankAccount(@RequestBody BankAccountRequest request,
            @AuthenticationPrincipal User user, @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.updateBankAccount(request, user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        bankAccountService.deleteBankAccount(id, user);
        return ResponseEntity.noContent().build();
    }
}
