package com.cos.photogramstart.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMRespDto<T> {

	// 공통 응답 DTO
	// 제네릭 타입
	
	private int code; // 1(성공), -1(실패)
	private String message;
	private T data;
	
	
	
	
}
