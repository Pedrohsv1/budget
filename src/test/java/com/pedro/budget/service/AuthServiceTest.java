package com.pedro.budget.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.pedro.budget.dto.SignInRequest;
import com.pedro.budget.dto.SignResponse;
import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.UserRole;
import com.pedro.budget.repository.CategoryRepository;
import com.pedro.budget.repository.PersonRepository;
import com.pedro.budget.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private PersonService personService;

    @Test
    @DisplayName("Sign in should return token + username + id when user is authenticated")
    void shouldSignin() {

        User user = new User("testuser", "password", UserRole.USER);

        SignInRequest signInRequest = new SignInRequest("testuser", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("mocked-jwt-token");

        SignResponse signResponse = authService.signIn(signInRequest);

        assertNotNull(signResponse);
        assertEquals("testuser", signResponse.username());
        assertEquals("mocked-jwt-token", signResponse.token());
    }

    @Test
    @DisplayName("Sign up should return token + username + id when user is created")
    void shouldSignUp() {
        User user = new User("newuser", "password", UserRole.USER);

        when(userRepository.save(any(User.class))).thenReturn(user);

        when(tokenService.generateToken(user)).thenReturn("mocked-jwt-token");
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        SignUpRequest signUpRequest = new SignUpRequest("new user name", "newuser", "password");
        SignResponse signResponse = authService.signUp(signUpRequest);

        assertNotNull(signResponse);
        assertEquals("newuser", signResponse.username());
        assertEquals("mocked-jwt-token", signResponse.token());
    }
}
