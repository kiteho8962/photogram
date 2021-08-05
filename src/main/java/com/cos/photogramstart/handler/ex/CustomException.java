package com.cos.photogramstart.handler.ex;

import java.util.Map;
public class CustomException extends RuntimeException {

	// html을 리턴하는 컨트롤러러
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}

}
