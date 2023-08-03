package fc5.i5e1server.domain.annual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AnnualUpdateReqDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String summary;
    private String reason;
}
