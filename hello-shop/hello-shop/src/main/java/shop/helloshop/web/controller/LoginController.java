package shop.helloshop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import shop.helloshop.domain.entity.Address;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.web.argumentresolver.Login;
import shop.helloshop.web.dto.LoginForm;
import shop.helloshop.web.dto.MemberDto;
import shop.helloshop.web.dto.MemberSessionDto;
import shop.helloshop.web.dto.SessionKey;
import shop.helloshop.web.exception.MemberException;
import shop.helloshop.web.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostConstruct
    public void initMethod() {
        Member member = new Member();
        member.setName("테스트");
        member.setEmail("asd123@naver.com");
        member.setPassword("123123");

        memberService.join(member);
    }

    @GetMapping("/")
    public String homepage(@Login MemberSessionDto sessionDto , Model model) {


        if (sessionDto == null) {
           return "home";
        }

        model.addAttribute("member", sessionDto);

        return "home";
    }

    @GetMapping("/member/add")
    public String addMemberForm(Model model) {
        MemberDto memberDto = new MemberDto();
        model.addAttribute("member", memberDto);
        return "/login/addMember";
    }

    @PostMapping("/member/add")
    public String addMember(@Validated @ModelAttribute("member") MemberDto memberDto,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/login/addMember";
        }

        Member member = Member.createMember(memberDto.getEmail(), memberDto.getPassword(), memberDto.getName(),
                new Address(memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode()));

        try {
            memberService.join(member);
        } catch (MemberException e) {
            bindingResult.reject("signUpFail",e.getMessage());
            return "/login/addMember";
        }


        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {

        LoginForm loginForm = new LoginForm();
        model.addAttribute("login", loginForm);

        return "/login/loginform";
    }



    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "/login/loginform";
        }

        MemberSessionDto memberSession = memberService.login(loginForm);

        if (memberSession == null) {
            bindingResult.reject("loginFail","Email 또는 Password 가 일치하지 않습니다.");
            return "/login/loginform";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionKey.LOGIN_MEMBER,memberSession);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
