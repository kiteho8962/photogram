package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional(readOnly = true) // 서비스가 끝날때쯤의 값이 바뀌었는지를 체크 안한다!
    public UserProfileDto 회원프로필(int pageUserId, int principalId) {
        UserProfileDto userProfileDto = new UserProfileDto();
        // select * from image where userId=:userId;
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId);// 1은 본인페이지, -1은 주인이 아님
        userProfileDto.setImageCount(userEntity.getImages().size());// 프론트단에서 연산을 안해도 된다.

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);
        
        return userProfileDto;
    }

    @Transactional
    public User 회원수정(int id, User user) {
        // 1. 영속화
        // 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 딕셉션 발동시킬게 or Else Throw()
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을수 없는 아이디 입니다.");
        });

        // 2. 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료)
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    } // 더티 체킹이 일어나서 업데이트가 완료됨.

}
