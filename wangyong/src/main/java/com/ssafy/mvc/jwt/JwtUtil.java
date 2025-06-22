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
    private final long accessTokenExpiration = 1000 * 60 * 5; // 5분 (Access Token)
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7; // 7일 (Refresh Token)

    // Access Token 생성 (5분)
    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token 생성 (7일)
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 만료검증
    public Jws<Claims> validateToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    // Refresh token 사용하여 userId 추출
    public String getUserIdFromToken(String token) {
        // 실제 JWT 토큰 문자열만 남김
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
