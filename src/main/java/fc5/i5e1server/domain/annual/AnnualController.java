package fc5.i5e1server.domain.annual;

import fc5.i5e1server.common.APIDataResponse;
import fc5.i5e1server.domain.model.Annual;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annual")
public class AnnualController {

    private final AnnualService annualService;

    public AnnualController(AnnualService annualService) {
        this.annualService = annualService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIDataResponse<List<AnnualPageDTO>>> getMyPageAnnual(@PathVariable Long userId) {
        return APIDataResponse.of(HttpStatus.OK, "마이페이지 연차 조회 성공", annualService.getAnnual(userId));
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<Annual>> createAnnual(
            @RequestBody AnnualCreateReqDTO annualCreateReqDTO,
            @RequestParam("memberId") Long memberId
    ) {
        Annual annual = annualService.createAnnual(annualCreateReqDTO, memberId);
        return APIDataResponse.empty(HttpStatus.CREATED, "연차 신청 성공");
    }
    @PutMapping("/{annualId}")
    public ResponseEntity<APIDataResponse<Annual>> performAction(
            @RequestBody AnnualActionReqDTO annualActionReqDTO,
            @PathVariable Long annualId
    ) {
        Annual annual = annualService.performAction(annualActionReqDTO, annualId);
        return APIDataResponse.empty(HttpStatus.OK, "연차 수정 성공");
    }
}