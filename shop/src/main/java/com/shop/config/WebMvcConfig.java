package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                //웹 브라우저에 입력하는 url 에 /images 로 시작하는 경우 uploadPath 에 설정한 다음으로 준으로 파일을 읽어 오도록 설정
                .addResourceLocations(uploadPath); //로컬컴퓨터에 저장된 파일을 읽어올 경로 설정

    }

}
