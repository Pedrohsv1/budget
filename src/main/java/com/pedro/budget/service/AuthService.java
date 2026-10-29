package com.pedro.budget.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.dto.SignResponse;
import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.exception.ResourceAlreadyExistsException;
import com.pedro.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CategoryService categoryService;
    private final AuthenticationManager authenticationManager; // Pedrohsv1: Cycle autowired when UserDetails
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder; // Pedrohsv1: @Bean PasswordEncoder in the SecurityConfigurations

    @Transactional
    public SignResponse signUp(SignUpRequest request) {
        if (userRepository.findByUsername(request.username()) != null) {
            throw new ResourceAlreadyExistsException("This username is already used by a user");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User user = userRepository.save(request.to(hashedPassword));

        categoryService.createDefaultCategories(user); // Pedrohsv1: Create default categories for the new user
        personService.createDefaultPerson(user); // Pedrohsv1: Create default person for the new user

        return SignResponse.from(tokenService.generateToken(user), user.getUsername(), user.getRole());
    }

    public SignResponse signIn(SignInRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        return SignResponse.from(tokenService.generateToken(user), user.getUsername(), user.getRole());
    }

    public String generateToken(User user) {
        return tokenService.generateToken(user);
    }
}
