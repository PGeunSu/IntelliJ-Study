//package Hello.HelloSpring;
//
//import Hello.HelloSpring.repository.MemberRepository;
//import Hello.HelloSpring.repository.MemoryMemberRepository;
//import Hello.HelloSpring.service.MemberService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringConfig {
//    //직접 설정 파일을 등록하여 스프링 컨테이너에ㅐ 스트링 빈을 등록하는 방법
//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }
//
//
//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
//}
