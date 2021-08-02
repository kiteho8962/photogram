package com.cos.photogramstart.web.api;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id, UserUpdateDto userUpdateDto) {
        User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
        System.out.println("==="+userUpdateDto+"===");
        return new CMRespDto<>(1, "회원 수정 완료", userEntity);
    }

}
