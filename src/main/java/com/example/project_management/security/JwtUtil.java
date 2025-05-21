package com.example.project_management.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key key;
    private final long expirationMs;

    public JwtUtil(
        @Value("${jwt.secret}") String base64Secret,
        @Value("${jwt.expiration}") long expirationMs
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList());

        if (userDetails instanceof MyUserDetails m) {
            claims.put("userId", m.getId());
            claims.put("name",   m.getName());
        }

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (JwtException|IllegalArgumentException e) {
            return false;
        }
    }
}
