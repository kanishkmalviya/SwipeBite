
package com.swipebite.user.service;

import java.util.Date;

import javax.crypto.SecretKey;
import jakarta.annotation.PostConstruct;
import java.security.MessageDigest;
import java.util.HexFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Secret key generate karna
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT Token generate karna
    public String generateToken(Long userId, String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
//fingerprint method
    @PostConstruct
    public void checkSecret() throws Exception {
        byte[] hash = MessageDigest.getInstance("SHA-256")
                .digest(secret.getBytes());

        System.out.println("JWT SECRET FINGERPRINT: "
                + HexFormat.of().formatHex(hash));
    }
    
    
    
    // Token se information read karna
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Token valid hai ya nahi check karna
    public boolean isTokenValid(String token) {

        try {
            extractClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
