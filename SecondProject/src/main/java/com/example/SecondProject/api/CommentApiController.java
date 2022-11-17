package com.example.SecondProject.api;

import com.example.SecondProject.dto.CommentDto;
import com.example.SecondProject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articlesId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) throws IllegalArgumentException{
        CommentDto createdDto = commentService.create(articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/api/comments/{id}")
    public  ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) throws IllegalArgumentException{
        CommentDto updateDto = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deleteDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}
