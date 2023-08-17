package fc5.i5e1server.domain.duty;

import fc5.i5e1server.domain.model.Duty;
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
public class DutyService {
    private final DutyRepository dutyRepository;
    private final ServiceUtil serviceUtil;

    public DutyPageListDTO getDuty() {
        List<Duty> dutyList = dutyRepository.findByMemberId(serviceUtil.getUserId());

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

    @Transactional
    public Duty createDuty(DutyCreateReqDTO dutyCreateReqDTO) {
        if (dutyCreateReqDTO.getDutyDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("당직일은 오늘 날짜 이후여야 합니다. dutyDate = " + dutyCreateReqDTO.getDutyDate());
        }

        if (serviceUtil.isDutyDateTaken(dutyCreateReqDTO.getDutyDate())) {
            throw new IllegalArgumentException("당직일이 이미 신청되어 있습니다. dutyDate = " + dutyCreateReqDTO.getDutyDate());
        }

        Member member = serviceUtil.findByUserId(serviceUtil.getUserId());

        Duty duty = new Duty();
        duty.create(dutyCreateReqDTO);
        duty.addMember(member);

        return dutyRepository.save(duty);
    }

    @Transactional
    public Duty updateDuty(DutyUpdateReqDTO dutyUpdateReqDTO, Long dutyId) {
        if(dutyUpdateReqDTO.getDutyDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("당직일은 오늘 날짜 이후여야 합니다. dutyDate = " + dutyUpdateReqDTO.getDutyDate());
        }
        if (serviceUtil.isDutyDateTaken(dutyUpdateReqDTO.getDutyDate())) {
            throw new IllegalArgumentException("당직일이 이미 신청되어 있습니다. dutyDate = " + dutyUpdateReqDTO.getDutyDate());
        }
        Duty duty = dutyRepository.findById(dutyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 당직은 존재하지않음 Id = " + dutyId));
        duty.update(dutyUpdateReqDTO);
        return dutyRepository.save(duty);
    }
}