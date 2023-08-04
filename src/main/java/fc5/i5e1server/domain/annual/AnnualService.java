package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Annual;
import fc5.i5e1server.domain.model.Member;
import fc5.i5e1server.domain.model.Status;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnualService {

    private final AnnualRepository annualRepository;
    private final MemberRepository memberRepository;

    public AnnualService(AnnualRepository annualRepository, MemberRepository memberRepository) {
        this.annualRepository = annualRepository;
        this.memberRepository = memberRepository;
    }

    public List<AnnualPageDTO> getAnnual(Long memberId) {
        List<Annual> annuals = annualRepository.findByMemberId(memberId);
        return annuals.stream()
                .map(annual -> {
                    AnnualPageDTO dto = new AnnualPageDTO();
                    dto.setStartDate(Date.valueOf(annual.getStartDate()));
                    dto.setEndDate(Date.valueOf(annual.getEndDate()));
                    dto.setStatus(annual.getStatus());
                    dto.setReason(annual.getReason());
                    dto.setSummary(annual.getSummary());
                    dto.setAnnualId(annual.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Annual createAnnual(AnnualCreateReqDTO annualCreateReqDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 멤버를 찾을 수없음 Id = " + memberId));

        Annual annual = new Annual();
        annual.updateByReq(annualCreateReqDTO);
        annual.addMember(member);
        member.reduceAnnualCount(annual.getSpentDays());

        return annualRepository.save(annual);
    }

    public Annual performAction(AnnualActionReqDTO annualActionReqDTO, Long annualId) {
        Annual annual = annualRepository.findById(annualId)
                .orElseThrow(() -> new IllegalArgumentException("No annual found with id " + annualId));

        if ("update".equals(annualActionReqDTO.getAction())) {
            annual.update(annualActionReqDTO);
        } else if ("cancel".equals(annualActionReqDTO.getAction())) {
            annual.cancel();
        } else {
            throw new IllegalArgumentException("Invalid action: " + annualActionReqDTO.getAction());
        }

        return annualRepository.save(annual);
    }

}