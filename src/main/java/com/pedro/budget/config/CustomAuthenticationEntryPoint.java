package com.pedro.budget.config;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.budget.exception.ApiError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

        ApiError apiError = new ApiError(
                java.time.LocalDateTime.now(),
                org.springframework.http.HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                authException.getMessage(), request.getRequestURI());
        ObjectMapper mapper = new ObjectMapper();
        OutputStream responseStream = response.getOutputStream();

        mapper.writeValue(responseStream, apiError);
        responseStream.flush();
    }
}