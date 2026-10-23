package com.pedro.budget.dto;

import com.pedro.budget.entity.User;
import com.pedro.budget.entity.UserRole;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotEmpty(message = "Name cannot be blank") String name,
        @NotEmpty(message = "Username cannot be blank") @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters") String username,
        @NotEmpty(message = "Password cannot be blank") @Size(min = 8, max = 20, message = "Password must be between 6 and 20 characters") String password) {
    public User to(String hashedPassword) {
        return new User(this.username, hashedPassword, UserRole.USER);
    }
}
