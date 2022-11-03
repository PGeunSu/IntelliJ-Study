package com.example.First.Project.controller;

import antlr.ASdebug.IASDebugStream;
import com.example.First.Project.dto.ArticleForm;
import com.example.First.Project.entity.Article;
import com.example.First.Project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sun.security.krb5.internal.ccache.MemoryCredentialsCache;

@Controller
public class ArticleController {
    @Autowired //스프링 부트가 이미 생성해 놓은 레파지토 객체를 가져옴(DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){

        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        System.out.println(form.toString());
        //1. DTO를 Entity로 변환
        Article article = form.toEntity();

        //2. Repository에게 Entity로 DB로 저장하게 함
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());
        return "";
    }

}
