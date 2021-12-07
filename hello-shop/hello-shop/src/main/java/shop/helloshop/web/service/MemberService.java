package shop.helloshop.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.web.exception.MemberException;
import shop.helloshop.web.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional //회원가입
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long id) {
      return memberRepository.findOne(id);
    }

    public List<Member> findAll() {
        return memberRepository.members();
    }

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

