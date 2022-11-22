package com.example.SecondProject.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD}) //Annotation의 적용대상
@Retention(RetentionPolicy.RUNTIME) //Annotation의 유지기간
public @interface RunningTime {


}
