package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //스프링 스큐리티에서 UserDetailService 를 구현하고 있는 클래스를 통해 로그인 구현
    //UserDetailService 인터페이스는 데이터베이스에서 회원정보를 가져오는 역할을 담당
    //loadUserByName() 메소드가 존재하며, 회원정보를 조회하여 사용자의 정보와 권한을 갖는 UserDetail 인터페이스를 반환
    //UserDetails : 스프링 시큐리티에서 회원정보를 담기 위해서 사용하는 인터 페이스
    //인터페이스를 직접 구현하거나 스프링 시큐리티에서 제공하는 User 클래스
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder().username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
