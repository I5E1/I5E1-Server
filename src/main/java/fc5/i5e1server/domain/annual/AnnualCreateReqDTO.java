package fc5.i5e1server.domain.annual;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AnnualCreateReqDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String summary;
    private String reason;
}