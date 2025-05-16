package com.ssafy.mvc.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	// 싱글톤 의존성 주입
	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		// 로그인 로직 처리
		System.out.println("check");
		LoginRequest user = userService.login(loginRequest);
		session.setAttribute("loginUser", user.getUserId());
		return ResponseEntity.ok("로그인 성공");
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 성공");
	}
}