package com.pedro.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.mapper.TransactionMapper;
import com.pedro.budget.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse createTransaction(@Validated(OnCreate.class) TransactionRequest request) {
        return transactionMapper.toResponse(transactionRepository.save(transactionMapper.toTransaction(request)));
    }

    public List<TransactionResponse> listTransactions() {
        return transactionMapper.toResponseList(transactionRepository.findAll());
    }
}
