package com.example.First.Project.ioc;

import org.springframework.stereotype.Component;

@Component //해당 클래스를 객체로 만들고, 이를 ioc 컨테이너에 등록하는 어노테이션
public class IngredientFactory {

    public Ingredient get(String menu){
        switch (menu){
            case "돈까스" :
                return new Pork("한돈 등심");
            case "1++ 안심" :
                return new Beef("한우");
            case "크리스피 치킨" :
                return new Chicken("국내산 10호 닭");
            default:
                return null;
        }
    }
}
