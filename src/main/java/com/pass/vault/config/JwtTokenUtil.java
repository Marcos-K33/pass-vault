package com.pass.vault.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pass.vault.entities.UserEntity;

@Component
public class JwtTokenUtil {

    private final Logger LOG = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateAccesToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername() + "," + user.getEmail())
                .withExpiresAt(new Date(new Date().getTime() + expiration))
                .sign(algorithm);
    }

    public boolean isValidAccessToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoder = verifier.verify(token);
            String userData = decoder.getSubject();
            return !userData.isEmpty();
        } catch (Exception e) {
            LOG.error("Ocurrió un error al validar el token", e);
        }
        return false;
    }

    public String getSubjectFromtoken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoder = verifier.verify(token);
            return decoder.getSubject();
        } catch (Exception e) {
            LOG.error("Ocurrió un error al obtener el subjet del token", e);
        }
        return "";
    }
}
