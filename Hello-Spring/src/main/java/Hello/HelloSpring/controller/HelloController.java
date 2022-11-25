package Hello.HelloSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!!");
        return "hello";
    }

    @GetMapping("/fruits")
    public String getFruit(Model model){
        Map<String, String> fruitMap = new HashMap<String, String>();
        fruitMap.put("fruit1","apple");
        fruitMap.put("fruit2","pear");
        fruitMap.put("fruit3","tomato");
        model.addAttribute("fruit",fruitMap);
        return "fruit/fruit";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    //단순 api 방식 - String 값 가지고 오기

    @GetMapping("hello-String")
    @ResponseBody
    public String HelloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-Api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
