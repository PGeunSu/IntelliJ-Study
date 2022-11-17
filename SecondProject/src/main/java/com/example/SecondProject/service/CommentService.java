package com.example.SecondProject.service;


import com.example.SecondProject.dto.CommentDto;
import com.example.SecondProject.entity.Article;
import com.example.SecondProject.entity.Comment;
import com.example.SecondProject.repository.ArticleRepository;
import com.example.SecondProject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId){
        return commentRepository.findByArticleId(articleId).stream().map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) throws IllegalArgumentException{
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생설 실패! 대상 게시글이 없습니다."));
        Comment comment = Comment.createComment(dto, article);
        Comment created = commentRepository.save(comment);
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) throws IllegalArgumentException{
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) throws IllegalArgumentException{
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }



}
