package com.example.First.Project.dto;

import com.example.First.Project.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
//리펙터링 : 결과에 관계없이 코드의 구조를 조정(lombok)
public class ArticleForm {

    private Long id;

    private String title;
    private String content;

    public Article toEntity() {

        return new Article(id,title,content);
    }
}
