package JpaBook.JpaShop.service;

import JpaBook.JpaShop.domain.Member;
import JpaBook.JpaShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; //javax 에서 지원하는 것보다 spring 에서 지원하는 어노테이션이 기능이 더 많다.

import java.util.List;

@Service
@Transactional(readOnly = true) //성능 최적화
@RequiredArgsConstructor //final 이 들어간 것만 생성자를 만들어 준다.
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;



    @Transactional
    //회원가입
    public Long join(Member member){

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
