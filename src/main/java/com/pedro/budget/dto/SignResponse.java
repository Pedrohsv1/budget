package com.pedro.budget.dto;

import com.pedro.budget.enums.UserRole;

public record SignResponse(String token, String username, UserRole role) {
    public static SignResponse from(String token, String username, UserRole role) {
        return new SignResponse(token, username, role);
    }
}