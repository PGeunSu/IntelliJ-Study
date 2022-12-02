package JpaBook.JpaShop.service;

import JpaBook.JpaShop.domain.Member;
import JpaBook.JpaShop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        //Given
        Member member = new Member();
        member.setName("KIM");
        //When
        Long saveId = memberService.join(member);
        //Then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws  Exception{
        //Given
        Member member1 = new Member();
        member1.setName("KIM");
        Member member2 = new Member();
        member2.setName("KIM");
        //When
        memberService.join(member1);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()->memberService.join(member2));
        //Then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}