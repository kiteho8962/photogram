package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 1. IoC 2. 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write(insert, update , delete)
	public User 회원가입(User user) {
		// 회원 가입 진행
		String rawPassword = user.getPassword();
		String enPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(enPassword);
		user.setRole("ROLE_USER"); // 관리자는 ROLE_ADMIN
		User userEntity = userRepository.save(user); // DB에 들어가고 난뒤에 응답받는것 잘받앗다고!
																		// 실제로 여기에서 DB로 들어감
		return userEntity;
	}
	
}
