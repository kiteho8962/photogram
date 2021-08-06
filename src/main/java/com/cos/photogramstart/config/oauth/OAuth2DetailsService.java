package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder; OAuth2DetailsService가 SecurityConfig보다 먼저 떠서
    // SecurityConfig에 있 Bean으로 등록되어있는 비크립트 패스워드 인코드를 못찾는다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("===Oaut2 왓니?===");
        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println(oAuth2User.getAttributes());
        Map<String, Object> userInfo = oAuth2User.getAttributes();
        String username = "facebook_" + (String) userInfo.get("id");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            // 페이스북 최초 로그인
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .email(email)
                    .role("ROLE_USER")
                    .build();
            return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes()); // 세션에 저장해줌
            // OAuth2로 로그인햇든 그냥 로그인햇든
            // 다 principalDetails에서 꺼내쓸수잇음
        }else {
            // 페이스북으로 이미 회원가입이 되어 있다는 뜻
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
        }
    }
}
