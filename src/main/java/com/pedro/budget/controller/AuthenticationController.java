package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.dto.SignResponse;
import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authorizationService;

    public AuthenticationController(AuthService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorizationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authorizationService.signIn(request));
    }
}
