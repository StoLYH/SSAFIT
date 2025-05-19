package com.ssafy.mvc.exception;

public class BoardException extends RuntimeException { 
	public BoardException() {}

	public BoardException(String message) {		
		super(message);			// 메세지 사용하면서 예외 터트리기 -> e.getmessage를 사용하기
	}
}
