package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.auth.dto.JoinDto;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fc5.i5e1server.common.APIDataResponse;
import org.springframework.http.HttpStatus;




import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody JoinDto joinDto) {
        return ResponseEntity.ok(memberService.signUp(joinDto));
    }
  
   @GetMapping("/user")
    public ResponseEntity<APIDataResponse<MemberInfoDTO>> myPage() {
        MemberInfoDTO memberInfoDTO = memberService.getMember(1L);
        return APIDataResponse.of(HttpStatus.OK, "마이페이지 조회 성공", memberInfoDTO);
    }

    @PutMapping("/user")
    public ResponseEntity<APIDataResponse<Void>> updateMyPage(@RequestBody MemberUpdateReqDTO request) {
        MemberInfoDTO memberInfoDTO = memberService.updateMember(1L, request);
        return APIDataResponse.empty(HttpStatus.OK, "마이페이지 정보 수정 성공");
    }
}