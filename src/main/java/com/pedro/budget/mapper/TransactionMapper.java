package com.pedro.budget.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.Transaction;
import com.pedro.budget.entity.User;

@Component
public class TransactionMapper {
    public Transaction toTransaction(TransactionRequest request, User user) {
        return new Transaction(request.getAmount(), request.getDescription(), request.getDate(), user);
    }

    public TransactionResponse toResponse(Transaction entity) {
        return new TransactionResponse(entity.getId(), entity.getAmount(), entity.getDescription(), entity.getDate());
    }

    public List<TransactionResponse> toResponseList(List<Transaction> entities) {
        if (entities.isEmpty() || entities == null) {
            return new ArrayList<>();
        }

        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
