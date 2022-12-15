package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 url 설정
                .defaultSuccessUrl("/") //로그인 성공 시 해당 url 반환
                .usernameParameter("email") //로그인 시 사용할 파라미터 이름으로 email 설정
                .failureUrl("/members/login/error") //로그인 실패 시 이동할 Url
                .and()
                .logout() //로그아웃 url
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/") //로그아웃 성공 시 이동할 url
        ;

        http.authorizeRequests() //시큐리티 처리에 HttpServletRequest 를 이용한다는 의미
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll() //permitAll() : 모든 사용자가 로그인 없이 경로 접근 가능
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN") //ADMIN 일 경우만 접근 가능
                .anyRequest().authenticated()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        //인증되지 않는 사용자가 리소스에 접근했을 떄 수행되는 핸들러 등록

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}