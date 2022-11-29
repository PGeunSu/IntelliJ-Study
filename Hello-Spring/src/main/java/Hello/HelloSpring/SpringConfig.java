package Hello.HelloSpring;

import Hello.HelloSpring.repository.JdbcMemberRepository;
import Hello.HelloSpring.repository.JdbcTemplateMemberRepository;
import Hello.HelloSpring.repository.MemberRepository;
import Hello.HelloSpring.repository.MemoryMemberRepository;
import Hello.HelloSpring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource,EntityManager em){

        this.dataSource = dataSource;
        this.em = em;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){

        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}


