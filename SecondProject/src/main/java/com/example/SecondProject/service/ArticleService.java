package com.example.SecondProject.service;

import com.example.SecondProject.dto.ArticleForm;
import com.example.SecondProject.entity.Article;
import com.example.SecondProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {// 기존 데이터 수정 방지(id입력 방지)
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != article.getId()) {
            log.info("잘못된 요청 id : {}, article : {}", id, article.toString());
            return null;
        }
        //업데이트 및 정상응답 (200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional //예외 발생시 롤백(원래대로 돌아감) 한다 .
    public List<Article> ceateArticle(List<ArticleForm> dtos) {
        //dto 묶음을 entity묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //Entity의 리스트로 만들 건데 스트림화 맵으로 하나씩 dto가 올 때마다 Entity로 맵핑된 것을 리스트로 변환
        /*List<Article> articleList = new ArrayList<>();
        for(int i = 0; i < dtos.size(); i++){
            ArticleForm dto = dtos.get(i);
            Article entity = dtos.toEntity();
            articleList.add(entity);
        }*/

        //entity 묶음을 DB에 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //articleList를 스트림화 시켜서 하나씩 반복, article이 올때 마다 저장
        /*for(int i = 0; i < articleList.size(); i++){
            Article article = articleList.get(i);
            articleRepository.save(article);
        }*/

        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패")
        );

        return articleList;


    }

}
