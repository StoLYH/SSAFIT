package com.ssafy.mvc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice	// 예외가 터지면 다 이쪽으로 온다. + 데이터 반환@ResponseBody
public class GlobalExceptionHandler {

	
	@ExceptionHandler(BoardException.class)	// 해당 예외 발생 시 여기로 온다
	public ResponseEntity<String> handleEmployeeException(BoardException e) {
		return ResponseEntity.internalServerError().body("에러 발생 : " + e.getMessage());
	}
	
	
	@ExceptionHandler(FileException.class)	// 해당 예외 발생 시 여기로 온다
	public ResponseEntity<String> handleEmployeeException(FileException e) {
		return ResponseEntity.internalServerError().body("에러 발생 : " + e.getMessage());
	}
	
	
	
	
}


