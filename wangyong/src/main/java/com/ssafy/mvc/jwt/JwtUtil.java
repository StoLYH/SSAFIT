package com.ssafy.mvc.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private String key = "wangyong-super-secret-hello-ssafy-this-key-is-long-enough";
    private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    private final long expiration = 1000 * 60 * 60; // 1시간

    //토큰생성
    public String generateToken(String userId) {
        return Jwts.builder().setSubject(userId).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    //토큰유효성검사
    public Jws<Claims> validateToken(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

    }




}
