package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // 외부에서 넣어주도록 코드를 변경
    }

    public Long join(Member member) {
        // filter already exist account
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("Already Exists..");
                });
    }

    // 전체 회원 조회 기능
    // 인터페이스에서는 save, findbyid, findbyname, findall 등 뭔가 되게 단순하게 이래저래만 하는 느낌인데
    // 서비스 클래스는 join, findMembers는 비즈니스에 가까운 네이밍을 한다. 실제로도 그런 네이밍을 사용해 주어야 한다.
    //
    public List<Member> findMembers() {
        // 전체회원 조회이므로 findAll()
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
