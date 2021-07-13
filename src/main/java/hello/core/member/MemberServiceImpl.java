package hello.core.member;

// 구현체 하나만 있으면 관례상 Impl 이라고 많이 씀.
// ctrl + shift + enter 하면 ;까지 들어감


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public void join(Member member) {
        this.memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
