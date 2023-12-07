package com.zelda.zeldaprojeto.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zelda.zeldaprojeto.userLogin.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "API Zelda";

    public String criarToken(UserLogin userLogin) {
        try {
            var algorito = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userLogin.getUsername())
                    .withExpiresAt(dataDeExpiracao())
                    .sign(algorito);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String token){
        try {
            var algorito = Algorithm.HMAC256(secret);
            return JWT.require(algorito)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Erro no token", exception);
        }
    }

    private Instant dataDeExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
