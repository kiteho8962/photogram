package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // Getter, Setter
public class SignupDto { // request 를위해서 담아두는 object
	
	@Size(min=2, max=20) // -> @Valid로 검증하는 옵션
	@NotBlank // -> @Valid로 검증하는 옵션
	private String username;
	@NotBlank // -> @Valid로 검증하는 옵션
	private String password;
	@NotBlank // -> @Valid로 검증하는 옵션
	private String email;
	@NotBlank // -> @Valid로 검증하는 옵션
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}

}
