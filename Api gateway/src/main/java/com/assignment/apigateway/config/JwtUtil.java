package com.assignment.apigateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {
    public static final String SECRET = "2a049c7b26086293f8582293818e50c78c976f0a3be4816c2d2915915892615c";

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