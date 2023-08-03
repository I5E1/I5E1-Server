package fc5.i5e1server.domain.duty;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DutyCreateReqDTO {
    private LocalDate dutyDate;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
