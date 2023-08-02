package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.model.Member;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberInfoDTO getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // 로그인 검증 로직 필요?
        return new MemberInfoDTO(member);
    }
}