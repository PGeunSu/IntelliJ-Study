package com.example.First.Project.service;

import com.example.First.Project.dto.CommentDto;
import com.example.First.Project.entity.Article;
import com.example.First.Project.entity.Comment;
import com.example.First.Project.repository.ArticleRepository;
import com.example.First.Project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    //article 데이터도 가져와야 됨
    private ArticleRepository articleRepository;



    public List<CommentDto> comments(Long articleId) {
        //댓글 목록 조회
       /*
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        //Entity -> Dto 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        //비어있는 dtos(arrayList)를 하나씩 for문으로 채워줌
        for(int i = 0; i < comments.size(); i++){

            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            //클래스 방식을 호출 하는 방식으로 만들ㄴ다
            //createCommentDto() : 생성 메소드를 만들어서 사용
            dtos.add(dto);
        }
        //반환
        return dtos;
        */

        //stream문법
        return commentRepository.findByArticleId(articleId).stream().map(comment -> CommentDto.createCommentDto(comment))
        //createCommentDto 생성메소드를 통해서q
                .collect(Collectors.toList());

    }

    @Transactional //중간에 문제가 생기면 롤백될 수 있게 설정
    public CommentDto create(Long articleId, CommentDto dto) throws IllegalArgumentException {
        //게시글 조회 및 예외 처리
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다"));
        //댓글 Entity 생성
        Comment comment = Comment.createComment(dto, article);//comment 클래스에서 createdComment를 작성해서 comment로 받아올 수 있다
        //댓글 Entity를 DB에 저장
        Comment created = commentRepository.save(comment);
        //DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public  CommentDto update(Long id, CommentDto dto)  throws  IllegalArgumentException{
        //댓글 조회 및 예의 발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다"));
        //댓글 수정
        target.patch(dto);
        //수정된 댓글을 DBd에 저장
        Comment updated = commentRepository.save(target);
        //DTO로 변경하여 반환
        return CommentDto.createCommentDto(updated); //생성 메서드를 통해서 변환
    }


    @Transactional
    public CommentDto delete(Long id) throws IllegalArgumentException{
        //댓글 조회 및 예의 발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다"));
        //삭제
        commentRepository.delete(target);
        //DTO로 변경하여 반환
        return CommentDto.createCommentDto(target);
    }
}
