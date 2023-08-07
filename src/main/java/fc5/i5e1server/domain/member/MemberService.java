package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.auth.dto.JoinDto;
import fc5.i5e1server.domain.auth.util.SecurityUtil;
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

    @Transactional
    public MemberInfoDTO getMember() {
        Long id = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 없음"));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return new MemberInfoDTO(member);
    }


    @Transactional
    public MemberInfoDTO updateMember(MemberUpdateReqDTO request) {
        Long id = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 없음"));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        member.updateTel(request.getTel());
        member.updatePassword(request.getPassword());
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