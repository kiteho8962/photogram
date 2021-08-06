package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC   
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.csrf().disable(); // csrf 토큰 해제!
		http.authorizeRequests()
			.antMatchers("/","/user/**", "/image/**", "/subscribe/**", "/coment/**", "/api/**").authenticated() // 인증이 필요한 주소들
			.anyRequest().permitAll() // 그외 주소는 모두 허용하겟다.
			.and() // 그리고?
			.formLogin() // 로그인 할껀데
			.loginPage("/auth/signin") // 로그인 페이지가 /auth/signin이다. // GET // Controller로 인해 jsp파일 리턴
			.loginProcessingUrl("/auth/signin") // POST // -> 스프링 시큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/")
				.and()
				.oauth2Login()// form로그인도 하는데, oauth2로그인도 할꺼야!
				.userInfoEndpoint()// oauth2로그인을 하면 최종응답 회원 정보를 바로 받을수 있다.
				.userService(oAuth2DetailsService); // 로그인을 정상적으로 처리하게되면 "/"으로 간다.
	}

}
