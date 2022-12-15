package com.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
             AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
    //ajax 의 경우 http request header 에 XML HttpRequest 라는 값이 세팅되어 오는데, 인증되지 않은 사용자가 ajax 로 리소스를 요청할 경우
    //Unauthorized 에러 발생 (나머지 경우는 로그인 페이지로 redirect)

}