package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.auth.util.SecurityUtil;
import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Duty;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DutyService {
    private final DutyRepository dutyRepository;
    private final MemberRepository memberRepository;

    public DutyPageListDTO getDuty() {
        Long memberId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 없음"));

        List<Duty> dutyList = dutyRepository.findByMemberId(memberId);

        List<DutyPageDTO> dutyPageDTOs = dutyList.stream()
                .map(duty -> {
                    DutyPageDTO dto = new DutyPageDTO();
                    dto.setDutyDate(Date.valueOf(duty.getDutyDate()));
                    dto.setStatus(duty.getStatus());
                    dto.setReason(duty.getReason());
                    return dto;
                })
                .collect(Collectors.toList());

        DutyPageListDTO annualPageDTO = new DutyPageListDTO();
        annualPageDTO.setDutyPageList(dutyPageDTOs);

        return annualPageDTO;
    }

    public Duty createDuty(DutyCreateReqDTO dutyCreateReqDTO) {
        Long memberId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 없음"));

        if(dutyCreateReqDTO.getDutyDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("당직일은 오늘 날짜 이후여야 합니다. dutyDate = " + dutyCreateReqDTO.getDutyDate());
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 멤버를 찾을 수없음 Id = " + memberId));
        Duty duty = new Duty();
        duty.create(dutyCreateReqDTO);
        duty.addMember(member);
        return dutyRepository.save(duty);
    }

    public Duty updateDuty(DutyUpdateReqDTO dutyUpdateReqDTO, Long dutyId) {
        if(dutyUpdateReqDTO.getDutyDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("당직일은 오늘 날짜 이후여야 합니다. dutyDate = " + dutyUpdateReqDTO.getDutyDate());
        }
        Duty duty = dutyRepository.findById(dutyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 당직은 존재하지않음 Id = " + dutyId));
        duty.update(dutyUpdateReqDTO);
        return dutyRepository.save(duty);
    }
}