package com.example.SecondProject.entity;

import com.example.SecondProject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
        if (dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 Id가 없어야함");
        }
        if (dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었음");
        }
        //Entity 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if (this.id != dto.getId()) { //url에서 던진 id와 json id가 다른 경우 예외 발생
            throw new IllegalArgumentException("댓글 수정실패! 잘못된 Id가 입력되었습니다");
        }
        //객체를 갱신
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
            //this의 nickname을 json nickname으로 수정
        }
        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }

}

