package com.example.First.Project.dto;

import com.example.First.Project.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {

    private Long id;

    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;



    public static CommentDto createCommentDto(Comment comment) {
        //static은 CommentDto 메소드를 생성하는데 사용
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                //Article()의 Id만 필요하므로
                comment.getNickname(),
                comment.getBody()
                //commentDto를 만드는 메소드를 추가했다.
        );
    }
}
