package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.model.Member;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //@Todo update랑 상동
    @Transactional
    public MemberInfoDTO getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return new MemberInfoDTO(member);
    }
    @Transactional
    //@Todo 로그인 완성후에 //Principal principal
    public MemberInfoDTO updateMember(Long id, MemberUpdateReqDTO request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        //@Todo 유효성검사 tel,password
        //Tel 뺼지 이야기 안뺴면 tel, password 변경 분리
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