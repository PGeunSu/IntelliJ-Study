package com.example.SecondProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sun.font.EAttribute;

@Controller
public class SecondProjectController {

    @GetMapping("/main")
    public String mainForm(Model model){

        model.addAttribute("username","Geunsu");
        return "main";
    }

}
