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
        return new MemberInfoDTO(member);
    }
    @Transactional
    public MemberInfoDTO updateMember(Long id, MemberUpdateReqDTO request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //중복 확인?
        member.updateTel((request.getTel()));
        member.updatePassword(request.getPassword());  // 로그인 완료 후 수정
        return new MemberInfoDTO(member);
    }
    public Member findByUserId(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
    }
}