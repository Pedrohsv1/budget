package com.pedro.budget.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedro.budget.dto.BankAccountReponse;
import com.pedro.budget.dto.BankAccountRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.repository.BankAccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountReponse createBankAccount(BankAccountRequest request, User user) {
        com.pedro.budget.entity.BankAccount bankAccount = bankAccountRepository.save(request.toEntity(user));
        return BankAccountReponse.from(bankAccount);
    }

    public Page<BankAccountReponse> listBankAccounts(User user, Pageable pageable) {
        return bankAccountRepository.findAllByUserId(user.getId(), pageable).map(BankAccountReponse::from);
    }

    public BankAccountReponse updateBankAccount(BankAccountRequest request, User user, UUID bankId) {
        com.pedro.budget.entity.BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(bankId, user.getId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        if (!bankAccount.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this bank account");
        }
        bankAccount.update(request.type(), request.description());

        bankAccountRepository.save(bankAccount);

        return BankAccountReponse.from(bankAccount);
    }

    public void deleteBankAccount(UUID bankId, User user) {
        com.pedro.budget.entity.BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(bankId, user.getId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);
    }
}
