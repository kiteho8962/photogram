package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Aspect // aop를 처리할수 있는 핸들러
@Component // RestController, Service .. 모든것들이 Component를 상속해서 만들어논것이기 때문
public class ValidationAdvice {
    // aop 공통기능
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") // 어떤함수가 실행전 후 둘다 할것들
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("======= web api 컨트롤러 =======");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                System.out.println("유효성 검사를 하는 함수 입니다.");
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사 실패함", errorMap);
                }
            }
        }
        // proceedingJoinPoint 모든 매서드및 매서드 내부에 접근 할수 있는 파라미터
        // ex) profile 함수보다 먼저 실행
        // proceedingJointPoint => profile 함수의 모든곳에 접근할 수 있는 변수
        return proceedingJoinPoint.proceed(); // 이때 profile 함수가 실행됨.
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("======= web 컨트롤러 =======");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                System.out.println("유효성 검사를 하는 함수 입니다.");
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사 실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
