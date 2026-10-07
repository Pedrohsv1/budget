package com.pedro.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.TransactionMapper;
import com.pedro.budget.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(
            TransactionRepository transactionRepository,
            TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse createTransaction(TransactionRequest request, User user) {
        return transactionMapper.toResponse(transactionRepository.save(transactionMapper.toTransaction(request, user)));
    }

    public List<TransactionResponse> listTransactions(User user) {
        return transactionMapper.toResponseList(transactionRepository.findAllByUserId(user.getId()));
    }
}
