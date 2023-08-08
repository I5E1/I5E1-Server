package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.auth.util.SecurityUtil;
import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Annual;
import fc5.i5e1server.domain.model.Member;
import fc5.i5e1server.domain.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnualService {

    private final AnnualRepository annualRepository;
    private final ServiceUtil serviceUtil;

    public AnnualPageListDTO getAnnual() {

        List<Annual> annuals = annualRepository.findByMemberId(serviceUtil.getUserId());
        List<AnnualPageDTO> pageList = annuals.stream()
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

        AnnualPageListDTO annualPageListDTO = new AnnualPageListDTO();
        annualPageListDTO.setAnnuals(pageList);
        return annualPageListDTO;
    }

    public Annual createAnnual(AnnualCreateReqDTO annualCreateReqDTO) {

        if (annualCreateReqDTO.getStartDate().isAfter(annualCreateReqDTO.getEndDate())) {
            throw new IllegalArgumentException("시작일은 종료일 이후일 수 없습니다");
        }

        if (annualCreateReqDTO.getStartDate().isBefore(LocalDate.now()) || annualCreateReqDTO.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("시작일과 종료일은 현재보다 미래이어야 합니다");
        }

        Member member = serviceUtil.findByUserId(serviceUtil.getUserId());

        if (serviceUtil.isDuplicatedAnnual(annualCreateReqDTO.getStartDate(), annualCreateReqDTO.getEndDate())) {
            throw new IllegalArgumentException("신청한 날짜 범위가 기존의 연차와 겹칩니다");
        }

        Annual annual = new Annual();
        annual.updateByReq(annualCreateReqDTO);

        if (annual.getSpentDays() > member.getAnnualCount()) {
            throw new IllegalArgumentException("요청한 휴가 일수가 연차 일수를 초과합니다");
        }

        annual.addMember(member);
        member.reduceAnnualCount(annual.getSpentDays());

        return annualRepository.save(annual);
    }

    public Annual performAction(AnnualActionReqDTO annualActionReqDTO, Long annualId) {
        Annual annual = annualRepository.findById(annualId)
                .orElseThrow(() -> new IllegalArgumentException("해당 연차는 존재하지않음 Id = " + annualId));

        if ("update".equals(annualActionReqDTO.getAction())) {
            annual.update(annualActionReqDTO);
        } else if ("cancel".equals(annualActionReqDTO.getAction())) {
            annual.cancel();
        } else {
            throw new IllegalArgumentException("action 제대로 넣어주세요 " + annualActionReqDTO.getAction());
        }

        return annualRepository.save(annual);
    }
}