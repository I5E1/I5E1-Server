package fc5.i5e1server.domain.auth;

import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    //username대신 email로 User를 불러오는 service메서드
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 회원이 존재하지 않습니다."));
        return createUser(member);
    }

    private org.springframework.security.core.userdetails.User createUser(Member member) {
        log.info("Member: {}", member);
        //User의 username대신 Member.id.toString()삽입
        return new org.springframework.security.core.userdetails.User(member.getId().toString(), member.getPassword(), AuthoritiesProvider.getAuthorityCollection());
    }
}