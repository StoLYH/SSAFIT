package com.ssafy.mvc.exception;

public class FileException  extends RuntimeException{
	public FileException() {}

	public FileException(String message) {
		super(message);
	}
}
