package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA 는 작동시 EntityManager 로 동작합니다. 이는 스프링 부트가 JPA 라이브러리를 받으면 생성하는 것으로,
    // 이전 강의에서 dataSource 를 주입받은 것처럼 생성된 manager 를 주입받으면 됩니다.

    private final EntityManager em; //EntityManager : Entity 를 관리하고 수행

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //persist : 영구 저장 명령어
        return member;
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name",Member.class).setParameter("name",name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
