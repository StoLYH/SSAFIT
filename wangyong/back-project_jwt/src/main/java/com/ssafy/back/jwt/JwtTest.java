package com.ssafy.back.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTest {
	public static void main(String[] args) throws InterruptedException {
		// 비밀키가 필요해!
		String key = "SSAFY_NonMajor_JavaTrack_SecretKey";
		SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

		Map<String, String> headers = new HashMap<>();
		headers.put("typ", "JWT");

		String token = Jwts.builder() // JWT를 만들 수 있는 빌더 객체
				.header() // 빌더 중에 헤더를 만드는 객체로 변경됨
				.add("name", "yu") // 연습용 보통은 페이로드에 넣음
				.add(headers).and() // 다시 JWT 빌더로 돌아왔다.
				.subject("jwt class").expiration(new Date(System.currentTimeMillis() + 3000)) // 3초 만큼 유효
				.signWith(secretKey) // 서명완료 -> Hash값 만들기 완료 [헤더+페이로드+비밀키]
				.compact();

		System.out.println(token);
		/////// 토큰 발급했음

		Thread.sleep(4000);
		// 토큰 유효한지 유효성 검증

		Jws<Claims> jwsClaims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

		System.out.println(jwsClaims.getHeader());
		System.out.println(jwsClaims.getPayload());

	}
}


/*
 * String token = Jwts.builder()
        .setHeaderParam("typ", "JWT")           // 헤더
        .setSubject("jwt class")                // subject (기본 클레임)
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료 시간
        .claim("userId", "hong123")             // ✅ payload 커스텀 클레임 추가
        .claim("role", "admin")                 // ✅ payload에 역할 추가
        .signWith(secretKey)                    // 서명
        .compact();   
 * */
