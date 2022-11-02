package com.example.First.Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sun.font.EAttribute;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){

        model.addAttribute("username","Geunsu");
        return "greeting";

    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){

        model.addAttribute("nickname","Geunsu");
        return "goodbye";
    }

}

