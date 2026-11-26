
package com.swipebite.gateway.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import java.security.MessageDigest;
import java.util.HexFormat;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    @PostConstruct
    public void checkSecret() throws Exception {
    	System.out.println("JWT CHECK METHOD CALLED");
        byte[] hash = MessageDigest.getInstance("SHA-256")
                .digest(secret.getBytes());

        System.out.println("JWT SECRET FINGERPRINT: "
                + HexFormat.of().formatHex(hash));
    }
    
}
