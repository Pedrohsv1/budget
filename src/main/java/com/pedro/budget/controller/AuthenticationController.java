package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.service.AuthorizationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthorizationService authorizationService,
            AuthenticationManager authenticationManager) {
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signIn(@RequestBody @Valid SignInRequest request) {
        authorizationService.signIn(request);

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
