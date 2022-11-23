package com.example.First.Project.objectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter //Json으로 만들어 주기위해 사용
public class Burger {

    private String name;
    private int price;
    private List<String> ingredients;



}
