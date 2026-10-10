package com.pedro.budget.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.entity.Transaction.TransactionDebit;

@Component
public class TransactionMapper {
    public Transaction toTransaction(TransactionRequest request, User user, Category category) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setCategory(category);
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());
        transaction.setUser(user);

        return transaction;
    }

    public TransactionResponse toResponse(Transaction entity) {
        if (entity.getTransactionType().equals("CREDIT")) {
            return toResponseCredit((TransactionCredit) entity);
        } else if (entity.getTransactionType().equals("DEBIT")) {
            return toResponseDebit((TransactionDebit) entity);
        } else {
            throw new IllegalArgumentException("Unknown transaction type: " + entity.getTransactionType());
        }
    }

    public List<TransactionResponse> toResponseList(List<Transaction> entities) {
        if (entities.isEmpty() || entities == null) {
            return new ArrayList<>();
        }

        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TransactionCredit toTransactionCredit(TransactionRequest request, User user, Category category) {
        TransactionCredit transactionCredit = new TransactionCredit();

        transactionCredit.setAmount(request.getAmount());
        transactionCredit.setCategory(category);
        transactionCredit.setCountInstallments(request.getInstallements());
        transactionCredit.setDate(request.getDate());
        transactionCredit.setDescription(request.getDescription());
        transactionCredit.setUser(user);

        return transactionCredit;
    }

    public TransactionResponse toResponseCredit(TransactionCredit transactionCredit) {
        TransactionResponse response = new TransactionResponse();

        response.setAmount(transactionCredit.getAmount());
        response.setCategory(transactionCredit.getCategory());
        response.setDate(transactionCredit.getDate());
        response.setDescription(transactionCredit.getDescription());
        response.setId(transactionCredit.getId());
        response.setInstallments(transactionCredit.getInstallments());
        response.setCountInstallments(transactionCredit.getCountInstallments());

        return response;
    }

    public TransactionDebit toTransactionDebit(TransactionRequest request, User user, Category category) {
        TransactionDebit TransactionDebit = new TransactionDebit();

        TransactionDebit.setAmount(request.getAmount());
        TransactionDebit.setCategory(category);
        TransactionDebit.setDate(request.getDate());
        TransactionDebit.setDescription(request.getDescription());
        TransactionDebit.setUser(user);

        return TransactionDebit;
    }

    public TransactionResponse toResponseDebit(TransactionDebit transactionDebit) {
        TransactionResponse response = new TransactionResponse();

        response.setAmount(transactionDebit.getAmount());
        response.setCategory(transactionDebit.getCategory());
        response.setDate(transactionDebit.getDate());
        response.setDescription(transactionDebit.getDescription());
        response.setId(transactionDebit.getId());

        return response;
    }
}
