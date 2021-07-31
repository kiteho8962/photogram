package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) { // 제네릭 타입
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할때는 Script가 좋다.(브라우저 or 클라이언트)
		// 2. Ajax 통신 - CMRespDto(개발자)
		// 3. Android통신 - CMRespDto(개발자)
		return Script.back(e.getErrorMap().toString());
	}

}
