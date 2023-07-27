package fc5.i5e1server.domain.admin;

import fc5.i5e1server.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
