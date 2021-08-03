package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller // 파일이니까~
public class ImageController {

	private final ImageService imageService;

	@GetMapping({"/", "/image/story"}) // 주소 2개를 받을수도 있음!
	public String story() {
		return "image/story";
	}
	
	@GetMapping({"/image/popular"})
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}

	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("===이미지가 첨부되지 않았습니다.===", null);
		}
		// 서비스 호출하고
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
	
}
