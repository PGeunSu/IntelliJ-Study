package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class MemoryMemberRepositoryTest {

     MemoryMemberRepository repository = new MemoryMemberRepository();


     @AfterEach //Transactional 대신 사용 //모든 Test 후에 clear 사용.
     public void afterEach(){
         repository.clearStore();
     }

    @Test
    void save() {
        Member member = new Member();
        member.setName("Spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        //optional 에서 id를 꺼내려면 get()을 사용해야 한다.
        // Assertions.assertEquals(result, member);
        assertThat(member).isEqualTo(result);
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get();
        //Spring1 이라는 이름으로 객체 찾기
        assertThat(result).isEqualTo(member1);

    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> result = repository.findAll(); //전체 목록 가져오기
        assertThat(result.size()).isEqualTo(2); //전체 목록 개수가 2개인지 확인
    }
}