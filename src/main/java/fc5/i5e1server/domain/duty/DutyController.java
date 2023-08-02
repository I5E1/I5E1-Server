package fc5.i5e1server.domain.duty;

import fc5.i5e1server.common.APIDataResponse;
import fc5.i5e1server.domain.annual.AnnualPageDTO;
import fc5.i5e1server.domain.annual.AnnualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DutyController {

    private final DutyService dutyService;

    public DutyController(DutyService dutyService) {
        this.dutyService = dutyService;
    }

    @GetMapping("/api/duty/{userId}")
    public ResponseEntity<APIDataResponse<List<DutyPageDTO>>> getMyPageAnnual(@PathVariable Long userId) {
        return APIDataResponse.of(HttpStatus.OK, "myPage duty", dutyService.getDuty(userId));
    }
}
