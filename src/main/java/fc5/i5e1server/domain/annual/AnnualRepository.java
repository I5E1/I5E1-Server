package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.model.Annual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnnualRepository extends JpaRepository<Annual, Long> {
    List<Annual> findByMemberId(@Param("member_id") Long memberId);

    boolean existsByStartDateAndMemberId(LocalDate startDate, Long memberId);
}
