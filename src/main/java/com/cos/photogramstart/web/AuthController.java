package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 DI 할때 사용 의존성 주입할때
@Controller // IoC에 등록이 되어있다는 의미이자, 파일을 return 하는 컨트롤러!
public class AuthController {
	
	
	
	//@Autowired // 해도되고
	private final AuthService authService;

//	기본 생성자를 만들어서 DI 하는 방법
//	public AuthController(AuthService authService) { // 이렇게 해도된다
//		this.authService = authService;
//	}

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원 가입 버튼 -> /auth/signup -> /auth/signin
	// 회원 가입 버튼 -> x(error 403) 이유: csrf 토큰!
	@PostMapping("/auth/signup") // 회원가입을 진행
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key = value (x-www-form-urlencoded)
													// BindingResult Valid로 검사한 결과값
		// ValidationAdvice 에서 처리함!!
		// @Valid 값들의 결과값에 대한 에러가 있는경우
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<>();
//			// getFieldErrors에 리스트 형태로 담김
//			for(FieldError error : bindingResult.getFieldErrors()) {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//				System.out.println("=================================");
//				System.out.println(error.getDefaultMessage());
//				System.out.println("=================================");
//			}
//			throw new CustomValidationException("유효성검사 실패함", errorMap);
//		} else {
			// User <- signupDto를 넣는다.
			User user = signupDto.toEntity(); // 연결고리
			authService.회원가입(user);
//			System.out.println(userEntity);
			return "auth/signin";
//		}
	}

}
