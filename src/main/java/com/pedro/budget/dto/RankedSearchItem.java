package com.pedro.budget.dto;

public record RankedSearchItem(
        SearchItem item,
        int score
) {
}
