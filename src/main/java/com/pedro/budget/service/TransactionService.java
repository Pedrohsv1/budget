package com.pedro.budget.service;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.Transaction.Installment;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionDebit;
import com.pedro.budget.mapper.TransactionMapper;
import com.pedro.budget.repository.CategoryRepository;
import com.pedro.budget.repository.Transaction.TransactionCreditRepository;
import com.pedro.budget.repository.Transaction.TransactionDebitRepository;
import com.pedro.budget.repository.Transaction.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDebitRepository transactionDebitRepository;
    private final TransactionCreditRepository transactionCreditRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, User user) {
        Category category = categoryRepository
                .findByIdAndUserId(request.getCategoryId(), user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category"));

        switch (request.getType().getTransactionType()) {
            case "Credit":
                TransactionCredit newTransactionCredit = transactionMapper.toTransactionCredit(request, user, category);

                List<Installment> installments = generateInstallments(newTransactionCredit, user);
                newTransactionCredit.setInstallments(installments);

                TransactionCredit transactionCredit = transactionCreditRepository.save(newTransactionCredit);

                return transactionMapper.toResponseCredit(transactionCredit);
            case "Debit":
                TransactionDebit transactionDebit = transactionDebitRepository
                        .save(transactionMapper.toTransactionDebit(request, user, category));

                return transactionMapper.toResponseDebit(transactionDebit);
            default:
                throw new InvalidParameterException("Cannot get this type");
        }
    }

    public List<TransactionResponse> listTransactions(User user) {
        return transactionMapper.toResponseList(transactionRepository.findAllByUserId(user.getId()));
    }

    public List<Installment> generateInstallments(TransactionCredit transactionCredit, User user) {
        Integer amount = transactionCredit.getAmount();
        Integer cents = transactionCredit.getAmount() / transactionCredit.getCountInstallments();

        List<Installment> installments = new ArrayList<>();

        for (int i = 1; i <= transactionCredit.getCountInstallments(); i++) {
            Installment installment = new Installment();

            if (i == transactionCredit.getCountInstallments()) {
                installment.setAmountCents(amount);
            } else {
                installment.setAmountCents(cents);

            }
            amount -= cents;

            installment.setDateTime(transactionCredit.getDate().plusMonths(i - 1));
            installment.setNumber(i);
            installment.setTransaction(transactionCredit);
            installment.setUser(user);

            installments.add(installment);
        }

        return installments;
    }
}
