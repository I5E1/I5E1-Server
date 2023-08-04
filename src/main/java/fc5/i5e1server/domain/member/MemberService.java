package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.auth.dto.JoinDto;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(JoinDto joinDto) {
        if (memberRepository.findByEmail(joinDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Member member = Member.builder()
                .name(joinDto.getUsername())
                .email(joinDto.getEmail())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .tel(joinDto.getTel())
                .build();
        return memberRepository.save(member);
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

        member.updateTel((request.getTel()));
        member.updatePassword(request.getPassword());  // 로그인 완료 후 수정
        return new MemberInfoDTO(member);
    }

    public Member findByUserId(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 아이디 = " + userId));
    }

    public boolean isDuplicateEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
}