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
import shop.helloshop.domain.entity.MemberGrade;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.service.ItemService;
import shop.helloshop.web.argumentresolver.Login;
import shop.helloshop.web.dto.*;
import shop.helloshop.web.exception.MemberException;
import shop.helloshop.domain.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final ItemService itemService;

    @PostConstruct
    public void initMethod() {
        Member member = new Member();
        member.setName("테스트");
        member.setEmail("asd123@naver.com");
        member.setPassword("123123");
        member.setAddress(new Address("1","2","3"));
        member.setMemberGrade(MemberGrade.MANAGER);

        memberService.save(member);
    }

    @GetMapping("/")
    public String homepage(@Login MemberSessionDto sessionDto, Model model) {


        if (sessionDto == null) {
            return "home";
        }

        List<Item> list = itemService.findList(FindSort.Item_Popularity_List);
        List<ItemViewForm> viewForm =new ArrayList<>();

        for (Item view : list){
            viewForm.add(ItemViewForm.createViewHome(view.getId(),view.getName(),view.getPrice(),view.getUploadFiles()));
        }

        model.addAttribute("items", viewForm);
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
    public String addMember(@Validated @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/login/addMember";
        }

        Member member = Member.createMember(memberDto.getEmail(), memberDto.getPassword(), memberDto.getName(),
                new Address(memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode()));

        try {
            memberService.save(member);
        } catch (MemberException e) {
            bindingResult.reject("signUpFail", e.getMessage());
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
            bindingResult.reject("loginFail", "Email 또는 Password 가 일치하지 않습니다.");
            return "/login/loginform";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionKey.LOGIN_MEMBER, memberSession);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/member/update/check")
    public String passwordCheckForm(Model model) {
        PasswordForm passwordForm = new PasswordForm();
        model.addAttribute("passwordForm", passwordForm);
        return "/login/passwordCheck";
    }

    @PostMapping("/member/update/check")
    public String passwordCheck(@Validated @ModelAttribute PasswordForm password, BindingResult bindingResult, @Login MemberSessionDto memberSessionDto
                               ) {

        if (bindingResult.hasErrors()) {
            return "/login/passwordCheck";
        }

        Member findMember = memberService.findOne(memberSessionDto.getId());

        if (!findMember.getPassword().equals(password.getPassword())) {
            bindingResult.reject("passwordMismatch", "비밀번호가 맞지 않습니다");
            return "/login/passwordCheck";
        }


        return "redirect:/member/update";
    }

    @GetMapping("/member/update")
    public String memberUpdateForm(@Login MemberSessionDto memberSessionDto,Model model) {

        Member findMember = memberService.findOne(memberSessionDto.getId());

        MemberDto updateForm = MemberDto.createUpdateForm(findMember.getEmail(), findMember.getName(),
                findMember.getAddress());
        model.addAttribute("update", updateForm);

        return "/login/updateForm";
    }

    //세션으로만 체크할경우 비밀번호 설정을 건너뛸수 있기때문에 보강 필요
    @PostMapping("/member/update")
    public String memberUpdate(@Validated @ModelAttribute("update") MemberDto memberDto,BindingResult bindingResult,
                               @Login MemberSessionDto memberSessionDto) {

        if (bindingResult.hasErrors()) {
            return "/login/updateForm";
        }

        memberDto.setId(memberSessionDto.getId());
        memberService.update(memberDto);

        return "redirect:/logout";
    }
}
