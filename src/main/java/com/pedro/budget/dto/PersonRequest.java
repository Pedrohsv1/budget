package com.pedro.budget.dto;

import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.dto.validation.OnUpdate;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonRequest(
        @NotBlank(message = "Name cannot be blank", groups = { OnCreate.class,
                OnUpdate.class }) @NotNull(message = "Name cannot be null", groups = { OnCreate.class }) String name,
        @NotNull(message = "Email cannot be null", groups = {
                OnCreate.class }) @Email(message = "Wrong email format", groups = { OnCreate.class,
                        OnUpdate.class }) String email) {
    public Person toEntity(User user) {
        return new Person(name, email, user);
    }
}
