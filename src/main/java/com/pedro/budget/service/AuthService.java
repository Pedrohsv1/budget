package com.pedro.budget.service;

import java.security.InvalidParameterException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.dto.SignResponse;
import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.SignMapper;
import com.pedro.budget.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CategoryService categoryService;
    private final AuthenticationManager authenticationManager; // Pedrohsv1: Cycle autowired when UserDetails is
    private final SignMapper signInMapper;

    @Transactional
    public SignResponse signUp(SignUpRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new InvalidParameterException("This username is already used by a user");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        User user = userRepository.save(signInMapper.toUser(request, hashedPassword));

        categoryService.createDefaultCategories(user); // Pedrohsv1: Create default categories for the new user

        return signInMapper.toResponse(tokenService.generateToken(user), user);
    }

    public SignResponse signIn(SignInRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        return signInMapper.toResponse(tokenService.generateToken((User) auth.getPrincipal()),
                (User) auth.getPrincipal());
    }

    public String generateToken(User user) {
        return tokenService.generateToken(user);
    }
}
