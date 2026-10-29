package com.pedro.budget.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pedro.budget.service.TokenService;
import com.pedro.budget.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {
    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("SecurityFilter should handle CredentialsExpiredException in customAuthenticationEntryPoint")
    void shouldHandleExpiredToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token_expirado");
        when(tokenService.validateToken(any(String.class)))
                .thenThrow(new TokenExpiredException("Token expirado", null));

        securityFilter.doFilter(request, response, filterChain);

        verify(customAuthenticationEntryPoint).commence(
                eq(request),
                eq(response),
                any(CredentialsExpiredException.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("SecurityFilter should handle BadCredentialException in customAuthenticationEntryPoint")
    void shouldHandleInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token_inválido");
        when(tokenService.validateToken(anyString())).thenThrow(new JWTVerificationException("Token inválido"));

        securityFilter.doFilter(request, response, filterChain);

        verify(customAuthenticationEntryPoint).commence(eq(request), eq(response), any(BadCredentialsException.class));
        verify(filterChain).doFilter(request, response);
    }
}
