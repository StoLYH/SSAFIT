package com.ssafy.mvc.controller;
import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dao.RefreshTokenDao;
import com.ssafy.mvc.model.dto.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RefreshTokenDao refreshTokenDao;

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response) {
		
		LoginRequest user = userService.login(loginRequest);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
		}

		session.setAttribute("loginUser", user.getUserId());
		
		// Access Token과 Refresh Token 발급
		String accessToken = jwtUtil.generateAccessToken(user.getUserId());
		String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

		// Refresh Token을 HttpOnly + Secure 쿠키로 설정
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true); // HTTPS 환경에서 활성화
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일
		response.addCookie(refreshTokenCookie);	// 클라이언트 전달
		
		System.out.println("로그인 시 Refresh Token 쿠키 설정: " + refreshToken.substring(0, 20) + "...");

		// Refresh Token을 데이터베이스에 저장
		RefreshToken refreshTokenEntity = new RefreshToken(
			user.getUserId(), 
			refreshToken, 
			LocalDateTime.now().plusDays(7)
		);
		refreshTokenDao.saveRefreshToken(refreshTokenEntity);

		// 응답 JSON으로 반환 (Access Token만 응답에 포함)
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", "로그인 성공");
		responseBody.put("token", accessToken);
		responseBody.put("userId", user.getUserId());
		
		System.out.println("초기 토큰 : " + accessToken);

		return ResponseEntity.ok(responseBody);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("응?");

		// 쿠키에서 Refresh Token 가져와서 userId 추출
		String userId = null;
		Cookie[] cookies = request.getCookies();
		
		System.out.println("쿠키 개수: " + (cookies != null ? cookies.length : 0));
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					try {
						userId = jwtUtil.getUserIdFromToken(cookie.getValue());
						System.out.println("Refresh Token에서 추출한 userId: " + userId);
						break;
					} catch (Exception e) {
						System.out.println("토큰 파싱 실패: " + e.getMessage());
						// 토큰 파싱 실패 시 무시
					}
				}
			}
		}
		
		System.out.println("최종 userId: " + userId);
		
		// Refresh Token 쿠키 삭제
		Cookie refreshTokenCookie = new Cookie("refreshToken", null);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true); // HTTPS 환경에서 활성화
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(0);
		response.addCookie(refreshTokenCookie);

		// 데이터베이스에서 Refresh Token 삭제
		if (userId != null) {
			refreshTokenDao.deleteByUserId(userId);
		}

		return ResponseEntity.ok("로그아웃 성공");
	}
}