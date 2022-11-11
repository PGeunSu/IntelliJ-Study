package com.example.SecondProject.controller;

import com.example.SecondProject.dto.ArticleForm;
import com.example.SecondProject.entity.Article;
import com.example.SecondProject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //로깅을 위한 lombok 어노테이션
@RestController
public class ArticleApiController {

    @Autowired //스프링 부트가 이미 생성해 놓은 레파지토리 객체를 자겨옴(DI)
    private ArticleService articleService;

    @GetMapping("/api/articles")
    //Article 목록조회
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return(created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> create(@PathVariable Long id, @RequestBody ArticleForm dto){

        Article updated = articleService.update(id,dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트렌젝션 -> 실패 -> 롤백
    @PostMapping("/api/transaction-test")
    //Article을 던지는데 List단위 -  RequestBody로 받는데 묶어서
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        //자세한 내용은 ArticleService에 맡긴다.(일을 시킨다. 파라메타에 dtos를 던지고- createList 받아옴)
        List<Article> createdList =  articleService.ceateArticle(dtos);
        return (createdList != null) ? //잘 반환이 되면
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
