package com.example.SecondProject.controller;
import com.example.SecondProject.dto.ArticleForm;
import com.example.SecondProject.dto.CommentDto;
import com.example.SecondProject.entity.Article;
import com.example.SecondProject.repository.ArticleRepository;
import com.example.SecondProject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 lombok 어노테이션
public class ArticleController {
    @Autowired //스프링 부트가 이미 생성해 놓은 레파지토 객체를 가져옴(DI)
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

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
        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}") //해당 url요청을 처리 선언
    public String show(@PathVariable Long id, Model model){ //url에서 id 변수를 가져옴
        log.info("id = " + id);
        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id). orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos",commentDtos);
        //3. 보여줄 페이지를 설정
        return "articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 Article을 가져온다. (3가지 방법)
        //List<Article> articleEntitiyList = (List<Article>) articleRepository.findAll();
        //Iterable<Article> articleEntitiyList = articleRepository.findAll();
        List<Article> articleEntitiyList = articleRepository.findAll();
        // == ArrayList<Article>
        //2. 가져온 Article의 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntitiyList);
        //3. 뷰 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit") //PathVariable : URL에서 id가져오기
    public String edit(@PathVariable Long id, Model model){
        //1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2. 모델에 데이터 등록
        model.addAttribute("article",articleEntity);
        //3. view 페이지 설정
        return "articles/edit";
    }

    //한글 깨질 떄
    //HELP : Edit custom ym options
    //-Dfile.encoding=UTF-8
    //-Dconsole.encoding=UTF-8

    //실행이 끝났으면 file -> "invalidate Cashes"

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1. DTO를 Entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2. Entity를 DB에 저장
        //2-1. DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터가 있다면, 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity);
        }
        //3. 수정 결과 페이지 redirect
        return "redirect:/articles/" + articleEntity.getId();
       }

       @GetMapping("/articles/{id}/delete")
       public String delete(@PathVariable Long id, RedirectAttributes rttr){
            log.info("삭제 요청이 들어왔습니다");
            //1. 삭제할 대상을 가져옴
            Article target = articleRepository.findById(id).orElse(null);
            log.info(target.toString());
            //2. 대상을 삭제
            if(target != null){
                articleRepository.delete(target);
                rttr.addFlashAttribute("msg","삭제가 완료되었습니다");
            }
            //3. 결과 페이지로 리다이렉트
           return "redirect:/articles";

       }

}
