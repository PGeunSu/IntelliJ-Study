package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByName(String name); //Optional 로 감싸서 반환
    Optional<Member> findById(Long id);
    List<Member> findAll();



}
