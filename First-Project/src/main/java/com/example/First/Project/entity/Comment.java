package com.example.First.Project.entity;

import com.example.First.Project.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동으로 순차적으로 생성
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;



    public static Comment createComment(CommentDto dto, Article article) throws IllegalArgumentException {



        //예외 처리
        if(dto.getId() != null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 Id가 없어야함");
        }
        if(dto.getArticleId() != article.getId()){
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었음");
        }
        //Entity 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }
}

