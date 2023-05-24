package likelion.springbootgongpal.controller;


import likelion.springbootgongpal.domain.Address;
import likelion.springbootgongpal.domain.Member;
import likelion.springbootgongpal.dto.MemberDto;
import likelion.springbootgongpal.service.MemberService;
import likelion.springbootgongpal.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/*
* 요청을 받고 응답 한다. controllers는 service를 이용하여 응답을 생성함
*
* */

@Controller // controller 역할 수행
@RequestMapping("members") // /members 경로로 되어있는 요청 처리
public class MemberController {
    private final MemberService memberService;

    // 생성자
    @Autowired // MemberServiceImpl 인스턴스 자동 주입
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }


    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new Member());
        return "members/createMemberForm";
    }

    @PostMapping("new")
    public String create(MemberDto.Create memberDto) {
        String name = memberDto.getName();
        String city = memberDto.getCity();
        String state = memberDto.getState();
        String street = memberDto.getStreet();
        String zipcode = memberDto.getZipcode();
        Address address = new Address(city, state, street, zipcode);
        Member member = Member.createMember(name, address);
        memberService.save(member);
        return "redirect:/";
    }

/*
* memberService.findAll()을 호출하여 모든 멤버를 가져온 후, memberList라는 이름으로 뷰에 전달 후 members/memberList 뷰를 반환하여 멤버 목록 전달
* */

    @GetMapping("")
    public String findAll(Model model) {
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList",memberList);
        return "members/memberList";
    }
}