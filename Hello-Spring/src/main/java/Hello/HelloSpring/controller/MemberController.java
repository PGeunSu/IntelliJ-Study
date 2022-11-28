package Hello.HelloSpring.controller;

import Hello.HelloSpring.domain.Member;
import Hello.HelloSpring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //모든 컨트롤러에서 사용하는 서비스 객체가 같게하기 위해서 스프링 컨테이너에 서비스 객체를 등록
    //@Autowired :  생성자에 이 어노테이션이 있으면 인자로 받은 값을 스프링 컨테이너에 있는 memberService 에 연결해준다.

    //한 번만 생성되어 하나의 회원 서비으 인스턴스를 각각의 컨트롤러들이 공유하는 것이 좋다.

    @GetMapping("/members/new")
    public String createForm(){

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){

        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        System.out.println("name is : " + member.getName());

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members",members);


        return "members/memberList";
    }

}
