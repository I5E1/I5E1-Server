package fc5.i5e1server.domain.annual;

import fc5.i5e1server.domain.model.Annual;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnualService {

    private final AnnualRepository annualRepository;

    public AnnualService(AnnualRepository annualRepository) {
        this.annualRepository = annualRepository;
    }

    public List<AnnualPageDTO> getAnnual(Long memberId) {
        List<Annual> annuals = annualRepository.findByMemberId(memberId);
        return annuals.stream()
                .map(annual -> {
                    AnnualPageDTO dto = new AnnualPageDTO();
                    dto.setStartDate(annual.getStartDate());
                    dto.setEndDate(annual.getEndDate());
                    dto.setStatus(String.valueOf(annual.getStatus()));
                    dto.setReason(annual.getReason());
                    dto.setSummary(annual.getSummary());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}