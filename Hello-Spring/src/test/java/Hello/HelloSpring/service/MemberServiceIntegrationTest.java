package Hello.HelloSpring.service;

import Hello.HelloSpring.domain.Member;
import Hello.HelloSpring.repository.MemberRepository;
import Hello.HelloSpring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 이전에는 @BeforeEeach 를 사용해 각 테스트를 실행하기 전 회원 서비스와 리포지토리를 직접 객체 생성,
    //주입했지만 이제는 @SpringBootTest 와 @Autowired 를 사용해 컨테이너에서 스프링을 통해 서비스와 리포지토리를 주입할 수 있습니다.
    //(회원 리포지토리는 이전의 MemoryMemberRepository 객체에서 JdbcMemberRepository 를 사용하는 MemberRepository 객체로 변경되었습니다.)
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOneMember(saveId).get();
        assertEquals(member.getName(),findMember.getName());
    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


}