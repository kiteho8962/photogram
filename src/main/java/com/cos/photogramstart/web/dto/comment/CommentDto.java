package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// NotNull = null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null, 공백(스페이스)까지 체크

@Data
public class CommentDto {
    @NotBlank
    private String content;
    @NotNull
    private Integer imageId;
    // toEntity 가 필요 없다.
}
