package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@ControllerAdvice // 모든 Exception을 다 낚아챔
@RestController
public class ControllerExceptionHandler {

	// 프론트에서는 script를 통해서 histroy.back()을 사용해 좀더 편리하게 해준다.
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) { // 제네릭 타입
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할때는 Script가 좋다.(브라우저 or 클라이언트)
		// 2. Ajax 통신 - CMRespDto(개발자)
		// 3. Android통신 - CMRespDto(개발자)
		return Script.back(e.getErrorMap().toString());
	}

//	서버 개발자는 제네릭 타입으로 리턴받는게편하고,
//	Generic 타입으로 에러타입 지정
//	@ExceptionHandler(CustomValidationException.class)	
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<>(-1, e.getMessage(), "문자열");
//	}

}
