package com.pedro.budget.dto;

import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonRequest(
        @NotBlank(message = "Name cannot be blank") @NotNull(message = "Name cannot be null") String name,
        @NotNull(message = "Email cannot be null") @Email(message = "Wrong email format") String email) {
    public Person toEntity(User user) {
        return new Person(name, email, user);
    }
}
