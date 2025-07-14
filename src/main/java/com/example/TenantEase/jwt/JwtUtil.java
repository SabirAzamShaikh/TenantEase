package com.example.TenantEase.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private static final String SECRET_KEY = "thisissecretkeywhichiwxanadadakdnaknidnakndanabdankibdajdkaniadkadnkbadknkilluse1234567890987654321";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).signWith(getKey(), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails) && !isTokenExpired(token);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
