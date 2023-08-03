package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.model.Duty;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DutyService {
    private final DutyRepository dutyRepository;

    public DutyService(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
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
}