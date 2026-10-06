package com.pedro.budget.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SignInRequest {
    @NotEmpty(message = "Username cannot be blank")
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    String username;

    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 6 and 20 characters")
    String password;
}
