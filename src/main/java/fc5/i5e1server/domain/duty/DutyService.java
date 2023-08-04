package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.annual.AnnualCreateReqDTO;
import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Annual;
import fc5.i5e1server.domain.model.Duty;
import fc5.i5e1server.domain.model.Member;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DutyService {
    private final DutyRepository dutyRepository;
    private final MemberRepository memberRepository;

    public DutyService(DutyRepository dutyRepository, MemberRepository memberRepository) {
        this.dutyRepository = dutyRepository;
        this.memberRepository = memberRepository;
    }

    public List<DutyPageDTO> getDuty(Long memberId) {
        List<Duty> dutyList = dutyRepository.findByMemberId(memberId);
        return dutyList.stream()
                .map(duty -> {
                    DutyPageDTO dto = new DutyPageDTO();
                    dto.setDutyDate(Date.valueOf(duty.getDutyDate()));
                    dto.setStatus(duty.getStatus());
                    dto.setReason(duty.getReason());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public Duty createDuty(DutyCreateReqDTO dutyCreateReqDTO ,Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 멤버를 찾을 수없음 Id = " + memberId));
        Duty duty = new Duty();
        duty.create(dutyCreateReqDTO);
        duty.addMember(member);
        return dutyRepository.save(duty);
    }

    public Duty updateDuty(DutyUpdateReqDTO dutyUpdateReqDTO, Long dutyId) {
        Duty duty = dutyRepository.findById(dutyId)
                .orElseThrow(() -> new IllegalArgumentException("No duty found with id " + dutyId));
        duty.update(dutyUpdateReqDTO);
        return dutyRepository.save(duty);
    }
}