package com.ssafy.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key = "SSAFY_wnag_yong_forprojectSecretKey";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));	// 비밀키를 만든다.
	
	// 토큰 생성 시 회언 id를 포함
	public String createToken(String user_id) {	
		// 토큰 유효기간 
		Date exp = new Date(System.currentTimeMillis()+ 1000*60*60); //1시간
		
		return Jwts.builder().header().add("type", "JWT").and()
				.claim("id", user_id).expiration(exp)
				.signWith(secretKey).compact();
	}
	
	
	//유효성 검증 (실제로 내용물을 확인하기 위함은 아님 / 에러나면 유효기간 지난것
	//이거 실행했을때 에러나면 유효기간 지난거....
	public Jws<Claims> vaildate(String token ){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
}
