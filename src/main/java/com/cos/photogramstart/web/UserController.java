package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto userProfileDto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("userProfileDto", userProfileDto);
		// 서비스 부를것 해당페이지의 주인
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails/*, Model model*/) {

		// 1. 추천
//		System.out.println("세션 정보"+principalDetails.getUser()); // 어노테이션으로 바로 접근


		// 2. 극혐
//		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//		System.out.println("직접 찾은 세션"+mPrincipalDetails.getUser()); // 어노테이션 없이 직접 세션 찾으려면

//		모델로 넘겨줘도 되지만 security tag lib 로 jsp에서 직접 접근 가능
//		model.addAttribute("principal", principalDetails.getUser());

		return "user/update";
	}

}
