package fc5.i5e1server.domain.member;

import fc5.i5e1server.common.APIDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/user")
    public ResponseEntity<APIDataResponse<MemberInfoDTO>> myPage() {
        MemberInfoDTO memberInfoDTO = memberService.getMember(1L);
        return APIDataResponse.of(HttpStatus.OK, "user data", memberInfoDTO);
    }
}