package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		// JPA에서 리턴된 userEntity를 여기서 받는다.
		this.user = user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		// JPA에서 리턴된 userEntity를 여기서 받는다.
		this.user = user;
	}


	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	// 권한: 한개가 아닐수도 있음!(3개이상의 권한일수도 있음)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()-> user.getRole());
		return collector;
//		collector.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				return user.getRole();
//			}
//		});
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 니계정이 만료가 되었니?
		// User Object에서 ex) getExpired가 true니? 이런식으로 여기서 설정 할수도 있음
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 니계정이 잠겼니??
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 니 비밀먼호가 1년이 지났는데 한번도 안바꾼거아니니?
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 니 계정이 활성화 되어있니?
		return true;
	}


}
