package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // AOP로 한곳에서 처리 -> ValidationAdvice에서!!
//        if(bindingResult.hasErrors()) {
//            Map<String, String> errorMap = new HashMap<>();
//            // getFieldErrors에 리스트 형태로 담김
//            for(FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
////                System.out.println("=================================");
////                System.out.println(error.getDefaultMessage());
////                System.out.println("=================================");
//            }
//            throw new CustomValidationApiException("유효성검사 실패함", errorMap);
//        }
        Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); // content, imageId, userId
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id) {
        commentService.댓글삭제(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
    }

}

