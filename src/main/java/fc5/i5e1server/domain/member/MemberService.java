package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.auth.dto.JoinDto;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(JoinDto joinDto){
        if(memberRepository.findByEmail(joinDto.getEmail()).orElse(null)!=null){
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

}
