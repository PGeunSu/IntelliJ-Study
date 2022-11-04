package com.example.First.Project.controller;
import com.example.First.Project.dto.ArticleForm;
import com.example.First.Project.entity.Article;
import com.example.First.Project.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 lombok 어노테이션
public class ArticleController {
    @Autowired //스프링 부트가 이미 생성해 놓은 레파지토 객체를 가져옴(DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){

        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        //System.out.println(form.toString());
        log.info(form.toString());
        //1. DTO를 Entity로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        //2. Repository에게 Entity로 DB로 저장하게 함
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "";
    }
    @GetMapping("/articles/{id}") //해당 url요청을 처리 선언
    public String show(@PathVariable Long id, Model model){ //url에서 id 변수를 가져옴
        log.info("id = " + id);
        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id). orElse(null);
        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);
        //3. 보여줄 페이지를 설정
        return "articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 Article을 가져온다.
        List<Article> articleEntitiyList = articleRepository.findAll();
        //2. 가져온 Article의 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntitiyList);
        //3. 뷰 페이지를 설정
        return "articles/index";
    }
}
