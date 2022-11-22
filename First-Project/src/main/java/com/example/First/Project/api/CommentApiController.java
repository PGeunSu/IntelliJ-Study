package com.example.First.Project.api;


import com.example.First.Project.annotation.RunningTime;
import com.example.First.Project.dto.CommentDto;
import com.example.First.Project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    //댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        //서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        //결과
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) throws IllegalArgumentException{

        //서비스에게 위임
        CommentDto createdDto = commentService.create(articleId, dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    //댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto ) throws IllegalArgumentException{
        //서비스에게 위임
        CommentDto updateDto = commentService.update(id,dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    //댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        //서비스에게 위임
        CommentDto deleteDto = commentService.delete(id);
        //결과 응답w
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}
