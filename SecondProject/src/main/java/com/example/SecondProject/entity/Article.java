package com.example.SecondProject.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.internal.build.AllowPrintStacktrace;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터 베이스 위임 (AUTO INCREMENT)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
       if(article.content != null){
           this.content = article.content;
       }
    }
}
