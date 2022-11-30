package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{

    //select m from Member m where m.name = ? 과 같은 JPQL 쿼리를 자동으로 생성
    @Override
    Optional<Member> findByName(String name);

}
