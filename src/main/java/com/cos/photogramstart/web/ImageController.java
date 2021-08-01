package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 파일이니까~
public class ImageController {

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
	
}
