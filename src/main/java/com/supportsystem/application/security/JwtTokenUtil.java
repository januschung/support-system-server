package com.supportsystem.application.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secret key for signing
    private final long jwtExpirationMs = 86400000; // 1 day expiration

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // Subject is the username
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // Parse and validate the token
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
