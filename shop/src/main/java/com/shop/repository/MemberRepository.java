package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    //회원가입 시 중복된 회원이 있는 지 검사하기 위해 이메일로 회원을 검사할 수 있도록 Query 메서드 작성
}
