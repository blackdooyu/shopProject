package shop.helloshop.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.web.dto.LoginForm;
import shop.helloshop.web.dto.MemberDto;
import shop.helloshop.web.dto.MemberSessionDto;
import shop.helloshop.web.exception.MemberException;
import shop.helloshop.domain.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional //회원가입
    public Long save(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional //업데이트
    public void update(MemberDto memberDto) {
        Member findMember = memberRepository.findOne(memberDto.getId());
        findMember.updateMember(memberDto);
    }


    public MemberSessionDto login(LoginForm loginForm) {
        return validateLogin(loginForm);
    }

    public Member findOne(Long id) {
      return memberRepository.findOne(id);
    }

    public List<Member> findAll() {
        return memberRepository.members();
    }


    /*
   Email 을 기준으로 List<Member> 를 가지고와서 List 가 비어있을경우 null 반환
   비어있지 않을경우 loginForm 에 있는 password 와 비교해서 맞을경우 세션에 저장해놓을 정보 생성
   틀릴경우 null 반환
     */
    private MemberSessionDto validateLogin(LoginForm loginForm) {

        List<Member> findEmail = memberRepository.findByEmail(loginForm.getEmail());
        if (findEmail.isEmpty()) {
            return null;
        }
        for (Member member : findEmail) {
            if (member.getPassword().equals(loginForm.getPassword())) {
                return new MemberSessionDto(member.getId(), member.getName(),member.getMemberGrade());
            }
        }

        return null;
    }

    /*
    회원가입 Check logic Email 또는 Name 이 같을경우 MemberException 을 던진다
     */
    private void validateMember(Member member) {
        List<Member> findEmail = memberRepository.findByEmail(member.getEmail());

        if (!findEmail.isEmpty()) {
            throw new MemberException("이미 존재하는 email 입니다");
        }

        List<Member> findName = memberRepository.findByName(member.getName());

        if (!findName.isEmpty()) {
            throw new MemberException("이미 존재하는 이름입니다.");
        }

    }

}

