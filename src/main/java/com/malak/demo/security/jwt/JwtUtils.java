package com.malak.demo.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.malak.demo.security.services.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

@Component
public class JwtUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    // Helper method to get the signing key
    private SecretKey getSigningKey() {
        // If your secret is Base64 encoded (recommended)
        try {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            // Fallback for non-Base64 secrets (not recommended for production)
            return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        }
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey()) // Modern API uses key directly
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder() // parserBuilder() instead of parser()
                .setSigningKey(getSigningKey())
                .build() // Need to build the parser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new JwtException("Invalid JWT signature", e);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new JwtException("Invalid JWT token", e);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new JwtException("JWT token is expired", e);
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            throw new JwtException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty", e);
        }
    }
}