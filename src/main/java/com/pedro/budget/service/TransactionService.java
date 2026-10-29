package com.pedro.budget.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

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
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionExpense;
import com.pedro.budget.entity.Transaction.TransactionIncome;
import com.pedro.budget.exception.ResourceNotFoundException;
import com.pedro.budget.repository.BankAccountRepository;
import com.pedro.budget.repository.CardRepository;
import com.pedro.budget.repository.CategoryRepository;
import com.pedro.budget.repository.CreditPurchaseRepository;
import com.pedro.budget.repository.PersonRepository;
import com.pedro.budget.repository.Transaction.TransactionCreditRepository;
import com.pedro.budget.repository.Transaction.TransactionExpenseRepository;
import com.pedro.budget.repository.Transaction.TransactionRepository;

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
    private final PersonRepository personRepository;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, User user) {
        Category category = categoryRepository
                .findByIdAndUserId(request.categoryId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find category"));

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.bankAccountId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find bank account"));

        Person person = personRepository.findByIdAndUserId(request.personId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find person"));

        switch (request.type()) {
            case INCOME:
                return TransactionResponse.from(createIncomeTransaction(user, category, bankAccount, person, request));
            case EXPENSE:
                return TransactionResponse.from(createExpenseTransaction(user, category, bankAccount, person, request));
            case CREDIT_EXPENSE:
                return TransactionResponse.from(createCreditTransaction(user, category, bankAccount, person, request));
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
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find transaction"));

        transactionRepository.delete(transaction);
    }

    public TransactionIncome createIncomeTransaction(User user, Category category, BankAccount bankAccount,
            Person person, TransactionRequest request) {
        TransactionIncome transactionIncome = new TransactionIncome(request.amount(), request.description(),
                request.date(), user, category, request.incomeType(), bankAccount, person);
        return transactionRepository.save(transactionIncome);
    }

    public TransactionExpense createExpenseTransaction(User user, Category category, BankAccount bankAccount,
            Person person, TransactionRequest request) {
        TransactionExpense transactionExpense = new TransactionExpense(request.amount(), request.description(),
                request.date(), user, category, request.expenseType(), bankAccount, person);
        return transactionExpenseRepository.save(transactionExpense);
    }

    public TransactionCredit createCreditTransaction(User user, Category category, BankAccount bankAccount,
            Person person, TransactionRequest request) {

        Card card = cardRepository.findByIdAndUserId(request.cardId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find card"));

        CreditPurchase creditPurchase = creditPurchaseRepository
                .save(new CreditPurchase(card, request.installmentTotal()));

        long totalAmount = request.amount();
        int totalInstallments = request.installmentTotal();

        long baseInstallmentAmount = totalAmount / totalInstallments;
        long remainder = totalAmount % totalInstallments;

        List<TransactionCredit> transactions = new ArrayList<>();

        for (int i = 1; i <= request.installmentTotal(); i++) {
            TransactionCredit transactionCredit = new TransactionCredit(
                    i == request.installmentTotal() ? baseInstallmentAmount + remainder : baseInstallmentAmount,
                    request.description(),
                    request.date().plusMonths(i - 1),
                    user,
                    category,
                    creditPurchase,
                    i,
                    request.installmentTotal(),
                    bankAccount,
                    person);
            transactions.add(transactionCredit);
        }

        List<TransactionCredit> savedTransactions = transactionCreditRepository.saveAll(transactions);

        return savedTransactions.get(0); // Return the first installment
    }

}
