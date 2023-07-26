package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
