package Hello.HelloSpring.service;

import Hello.HelloSpring.domain.Member;
import Hello.HelloSpring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach //Transactional 대신 사용 //모든 Test 후에 clear 사용.
    public void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    void join() {
        //given 어떤 상황이 주어지고
        Member member = new Member();
        member.setName("hello");
        //when 이걸 실행했을 때
        Long saveId = memberService.join(member);
        //then 결과가 이제 나와야함
        Member findMember = memberService.findOneMember(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }
    //중복 회원 검사
    @Test
    public void DuplicateMember(){
        //given
        Member member1 = new Member();
        member1.setName("Spring");

        Member member2 = new Member();
        member2.setName("Spring");
        //when
        memberService.join(member1);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo(("이미 존재하는 회원입니다."));
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail();
//        }catch (IllegalArgumentException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
        //then

    }

    @Test
    void findAllMember() {
    }

    @Test
    void findOneMember() {
    }
}