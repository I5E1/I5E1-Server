package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DutyPageDTO {
    private Date dutyDate;
    private Status status;
    private String reason;
}