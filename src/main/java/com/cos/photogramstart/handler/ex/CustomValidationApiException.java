package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException {
    // 제일 처음 값을 받을때

    // 객체를 구별할때!!
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        // 나만의 커스텀 Exception
        super(message); // 부모한테 던지면 원래있던 getMessage가 받아서 리턴
        this.errorMap = errorMap;
    }

    public CustomValidationApiException(String message) {
        super(message);
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
