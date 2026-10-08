package com.pedro.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.TransactionRequest;
import com.pedro.budget.dto.TransactionResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.TransactionMapper;
import com.pedro.budget.repository.CategoryRepository;
import com.pedro.budget.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionRequest request, User user) {
        Category category = categoryRepository
                .findByIdAndUserId(request.getCategoryId(), user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category"));

        return transactionMapper
                .toResponse(transactionRepository.save(transactionMapper.toTransaction(request, user, category)));
    }

    public List<TransactionResponse> listTransactions(User user) {
        return transactionMapper.toResponseList(transactionRepository.findAllByUserId(user.getId()));
    }
}
