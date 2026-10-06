package com.pedro.budget.service;

import java.security.InvalidParameterException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Void signIn(SignInRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new InvalidParameterException("This username is already used by a user");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        User user = new User(request.getUsername(), hashedPassword, request.getRole());

        userRepository.save(user);

        return null;
    }
}
