package com.pedro.budget.dto;

import com.pedro.budget.entity.UserRole;

import lombok.Value;

@Value
public class SignResponse {
    String token;
    String username;
    UserRole role;
}
