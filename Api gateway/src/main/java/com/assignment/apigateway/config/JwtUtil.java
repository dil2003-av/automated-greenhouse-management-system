package com.assignment.apigateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {
    public static final String SECRET = "MyCustomSecretKeyForAGMSProject12345678901234567890";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


//    private Key getSignKey() {
//        byte[] keyBytes = SECRET.getBytes(java.nio.charset.StandardCharsets.UTF_8);
//        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
//    }

}