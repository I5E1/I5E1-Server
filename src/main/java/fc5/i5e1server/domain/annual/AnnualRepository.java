package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.model.Annual;
import fc5.i5e1server.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualRepository extends JpaRepository<Annual, Long> {
    List<Annual> findByMember(Long userId);
}
