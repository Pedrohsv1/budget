package com.pedro.budget.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedro.budget.entity.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) throws RuntimeException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT
                .create()
                .withIssuer("auth-api")
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);

        return token;

    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();

    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-3"));
    }
}
