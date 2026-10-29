package com.pedro.budget.service;

import java.security.InvalidParameterException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.BankAccount;
import com.pedro.budget.entity.Card;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.CreditPurchase;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionExpense;
import com.pedro.budget.entity.Transaction.TransactionIncome;
import com.pedro.budget.repository.BankAccountRepository;
import com.pedro.budget.repository.CardRepository;
import com.pedro.budget.repository.CategoryRepository;
import com.pedro.budget.repository.CreditPurchaseRepository;
import com.pedro.budget.repository.Transaction.TransactionCreditRepository;
import com.pedro.budget.repository.Transaction.TransactionExpenseRepository;
import com.pedro.budget.repository.Transaction.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionExpenseRepository transactionExpenseRepository;
    private final TransactionCreditRepository transactionCreditRepository;
    // Repositories to fetch related entities
    private final CategoryRepository categoryRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final CreditPurchaseRepository creditPurchaseRepository;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, User user) {
        Category category = categoryRepository
                .findByIdAndUserId(request.categoryId(), user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category"));

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.bankAccountId(), user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find bank account"));

        switch (request.type()) {
            case INCOME:
                if (request.incomeType() == null) {
                    throw new InvalidParameterException("Income type is required");
                }

                TransactionIncome transactionIncome = new TransactionIncome(request.amount(), request.description(),
                        request.date(), user, category, request.incomeType(), bankAccount);

                TransactionIncome savedTransactionIncome = transactionRepository.save(transactionIncome);

                return TransactionResponse.from(savedTransactionIncome);
            case EXPENSE:
                if (request.expenseType() == null) {
                    throw new InvalidParameterException("Expense type is required");
                }

                TransactionExpense transactionExpense = new TransactionExpense(request.amount(), request.description(),
                        request.date(), user, category, request.expenseType(), bankAccount);

                TransactionExpense savedTransactionExpense = transactionExpenseRepository.save(transactionExpense);

                return TransactionResponse.from(savedTransactionExpense);
            case CREDIT_EXPENSE:
                if (request.installmentNumber() == null || request.installmentTotal() == null) {
                    throw new InvalidParameterException("Installment number and total are required");
                }

                Card card = cardRepository.findByIdAndUserId(request.cardId(), user.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Cannot find card"));

                CreditPurchase creditPurchase = creditPurchaseRepository
                        .save(new CreditPurchase(card, request.installmentTotal()));

                var amount = request.amount();
                var cents = amount / request.installmentTotal();

                for (int i = 1; i <= request.installmentTotal(); i++) {
                    TransactionCredit transactionCredit = new TransactionCredit(
                            i == request.installmentTotal() ? amount : cents,
                            request.description(),
                            request.date().plusMonths(i - 1),
                            user,
                            category,
                            creditPurchase,
                            i,
                            request.installmentTotal(),
                            bankAccount);
                    transactionCreditRepository.save(transactionCredit);
                    amount -= cents;

                    if (i == request.installmentTotal()) {
                        return TransactionResponse.from(transactionCredit); // Pedrohsv1: Return the first installment
                    }
                }

                throw new InvalidParameterException("Cannot create credit transaction");
            default:
                throw new InvalidParameterException("Cannot get this type");
        }
    }

    public Page<TransactionResponse> listTransactions(User user, Pageable pageable) {
        Page<TransactionResponse> page = transactionRepository.findAllByUserId(user.getId(), pageable)
                .map(TransactionResponse::from);
        return page;
    }

    @Transactional
    public void deleteTransaction(User user, java.util.UUID transactionId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find transaction"));

        transactionRepository.delete(transaction);
    }
}
