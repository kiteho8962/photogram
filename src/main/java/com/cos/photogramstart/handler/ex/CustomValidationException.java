package com.cos.photogramstart.handler.ex;

import java.util.Map;
// RuntimeException을 상속받고 여기서 커스터마이징한다.
public class CustomValidationException extends RuntimeException {

	
	// 객체를 구별할때!!
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		// 나만의 커스텀 Exception
		super(message); // 부모한테 던지면 원래있던 getMessage가 받아서 리턴
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}

}
