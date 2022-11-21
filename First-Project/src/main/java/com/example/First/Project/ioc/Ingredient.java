package com.example.First.Project.ioc;

public abstract class Ingredient {

    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
ㅂ