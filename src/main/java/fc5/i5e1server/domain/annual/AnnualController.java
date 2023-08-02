package fc5.i5e1server.domain.annual;

import fc5.i5e1server.common.APIDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnnualController {

    private final AnnualService annualService;

    public AnnualController(AnnualService annualService) {
        this.annualService = annualService;
    }

    @GetMapping("/api/annual/{userId}")
    public ResponseEntity<APIDataResponse<List<AnnualPageDTO>>> getMyPageAnnual(@PathVariable Long userId) {
        return APIDataResponse.of(HttpStatus.OK, "myPage annual", annualService.getAnnual(userId));
    }
}