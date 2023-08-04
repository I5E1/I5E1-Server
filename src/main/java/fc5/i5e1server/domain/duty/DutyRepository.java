package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DutyRepository extends JpaRepository<Duty, Long> {
    List<Duty> findByMemberId(@Param("member_id") Long memberId);
}