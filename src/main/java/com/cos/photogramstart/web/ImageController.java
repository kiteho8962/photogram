package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller // 파일이니까~
public class ImageController {

	private final ImageService imageService;

	@GetMapping({"/", "/image/story"}) // 주소 2개를 받을수도 있음!
	public String story() {
		return "image/story";
	}

	//API 구현한다면 -> (요청을 브라우저에서 하는게 아니라, 안드로이드, IOS 요청인경우)
	@GetMapping({"/image/popular"})
	public String popular(Model model) {

		//Api는 데이터를 리턴하는 서버!
		// 이건 Ajax를 쓰지않을거라 그냥 컨트롤러임
		List<Image> images = imageService.인기사진();

		model.addAttribute("images", images);

		return "image/popular";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}

	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 얘는 어쩔수가 없음.
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("===이미지가 첨부되지 않았습니다.===", null);
		}
		// 서비스 호출하고
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
	
}
