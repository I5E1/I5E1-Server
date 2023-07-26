package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyRepository extends JpaRepository<Duty, Long> {
}
