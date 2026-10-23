package com.pedro.budget.dto;

import com.pedro.budget.entity.UserRole;

public record SignResponse(String token, String username, UserRole role) {
    public static SignResponse from(String token, String username, UserRole role) {
        return new SignResponse(token, username, role);
    }
}