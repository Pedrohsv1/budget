package com.pedro.budget.dto;

import com.pedro.budget.enums.SearchItemType;

import java.util.UUID;

public record SearchItem(
        UUID id,
        SearchItemType type,
        String title,
        String subtitle
) {
}
