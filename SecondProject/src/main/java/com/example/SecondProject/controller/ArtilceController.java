package com.example.SecondProject.controller;

import com.example.SecondProject.dto.ArticleForm;
import com.example.SecondProject.entity.Article;
import com.example.SecondProject.repository.ArticleRepository;
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
public class ArtilceController {

    @Autowired //스프링 부트가 이미 생성해 놓은 레파지토리 객체를 자겨옴(DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){

        return "articles/new";
    }

    @PostMapping("articles/create")
    public String creteArticle(ArticleForm form){

        log.info(form.toString());
        //1. DTO를 Entity 로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에게 Entity를 DB에 저장
        Article saved = articleRepository.save(article);
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

        //1. 모든 Article을 가져옴
        List<Article> articleEntityList = articleRepository.findAll();
        //2. 가져온 Article의 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);
        //3. 뷰 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2. 모델에 데이터 등록
        model.addAttribute("article",articleEntity);
        //3. view 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null){
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }
}
