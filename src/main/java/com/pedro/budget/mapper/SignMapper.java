package com.pedro.budget.mapper;

import org.springframework.stereotype.Component;

import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.dto.SignResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.UserRole;

@Component
public class SignMapper {
    public SignResponse toResponse(String token, User user) {
        return new SignResponse(token, user.getUsername(), user.getRole());
    }

    public User toUser(SignUpRequest request, String hashedPassword) {
        return new User(request.getUsername(), hashedPassword, UserRole.USER);
    }
}
