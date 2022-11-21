package com.example.First.Project.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {


    private IngredientFactory ingredientFactory;
    //셰프가 식재료 공장과 협업하기 위한 DI(동작에 필요한 객체를 외부에서 받아옴)
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    //문제1 : 식재료와 셰프의 의존성이 큼
    //문제2 : 셰프와 식재료 사이에, 조달 공장을 두어 의존성을 낮춤 - 요구사항 변경에 유연하도록 코드 개선

    public String cook(String menu){
        //재료분석
        //Pork pork = new Pork("한돈 등심");
        //Beef beef = new Beef("한우");
        //요리완성
        //return pork.getName() + "으로 만든 " + menu;
        //return "레드와인 소스를 곁들인 " + beef.getName() + menu;

        Ingredient ingredient = ingredientFactory.get(menu);
        return ingredient.getName() + "으로 만든 " + menu;
    }

    //외부에서 값을 삽입 받는 방법 - 즉 Di 방식으로 코드를 개선하면 외부에서 요규사항이 변하더라도 내부에 코드가 변하지 않게 요연하게 코드를 개선
    //ioc 컨테이너에 필요한 객체를 등록(Component)하고 Autowired를 통해서 DI를 가져올 수 있다


}
