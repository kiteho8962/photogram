package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult, // @Valid가 적혀있는 다음 파라미터에 꼭 위치해야함!!!
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            // getFieldErrors에 리스트 형태로 담김
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
//                System.out.println("=================================");
//                System.out.println(error.getDefaultMessage());
//                System.out.println("=================================");
            }
            throw new CustomValidationApiException("유효성검사 실패함", errorMap);
        }else {
            User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); // 세션 정보 변경
            return new CMRespDto<>(1, "회원 수정 완료", userEntity);
            // 응답시에 userEntity의 모든 getter함수가 호출되고 JSON으로 파싱하여 응답해야한다. -> 그로인해 getImages가 호출이되는데 문제가된다.
        }


    }

}
