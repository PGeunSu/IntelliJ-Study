package Hello.HelloSpring.service;

import Hello.HelloSpring.domain.Member;
import Hello.HelloSpring.repository.MemberRepository;
import Hello.HelloSpring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private MemberRepository memberRepository;
    //service 를 만들기 위해서는 Repository 가 있어야함

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    private void validateDuplicateMember(Member member) {

        //같은 이름이 있는 중복회원 X
        // Optional<Member> result = memberRepository.findByName(member.getName());
        // result.ifPresent(m -> {
        //이미 Optional 로 받았기 때문에 따로 변수 지정을 안 해줘도 가능하다.

        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //전체회원 조회
    public List<Member> findAllMember(){
        return memberRepository.findAll();
    }

    //회원 조회
    public Optional<Member> findOneMember(Long memberId){
        return memberRepository.findById(memberId);
    }


}
