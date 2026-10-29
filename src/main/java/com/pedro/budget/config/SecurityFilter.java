package com.pedro.budget.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pedro.budget.service.TokenService;
import com.pedro.budget.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final TokenService tokenService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.recoveryToken(request);

        if (token != null) {
            try {
                String username = tokenService.validateToken(token);
                UserDetails user = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenExpiredException ex) {
                customAuthenticationEntryPoint.commence(request, response,
                        new CredentialsExpiredException("Credentials expired"));
            } catch (JWTVerificationException ex) {
                customAuthenticationEntryPoint.commence(request, response,
                        new BadCredentialsException("Bad credentials"));
            }
        }

        filterChain.doFilter(request, response);
    }

    public String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null)
            return null;

        return authHeader.replace("Bearer ", "");
    }

}
