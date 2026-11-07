package com.pedro.budget.dto;

import java.util.List;

public record SearchResponse(List<TransactionResponse> transactions, List<BankAccountResponse> accounts,
        List<CardResponse> cards,
        List<CategoryResponse> categories, List<PersonResponse> persons) {
}
