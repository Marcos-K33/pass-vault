package com.pass.vault.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pass.vault.entities.UserEntity;

@Component
public class JwtTokenUtil {

    // private static final long EXPIRATION = 24 * 60 * 60 * 1000; // 24 hours
    // private static final String SECRET = "6vSbxlTZygSMdHmRvJeGCy92008TWvju";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateAccesToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        String token = JWT.create()
                .withSubject(user.getUsername() + ", " + user.getEmail())
                .withExpiresAt(new Date(new Date().getTime() + expiration))
                .sign(algorithm);
        return token;
    }

    public boolean isValidAccessToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoder = verifier.verify(token);
            String userData = decoder.getSubject();
            return !userData.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getSubjectFromtoken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoder = verifier.verify(token);
            String userData = decoder.getSubject();
            return userData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
