package JpaBook.JpaShop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    //@Slf4j 사용
    //Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String Home(){
        log.info("home controller");
        return "home";
    }
}