package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.web.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User 회원수정(int id, User user) {
        // 1. 영속화
        User userEntity = userRepository.findById(id).get();
        // 2. 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료)
        return null;
    }

}
