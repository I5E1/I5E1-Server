package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class AnnualPageDTO {
    private Date startDate;
    private Date endDate;
    private Status status;
    private String reason;
    private String summary;
    private Long annualId;
}