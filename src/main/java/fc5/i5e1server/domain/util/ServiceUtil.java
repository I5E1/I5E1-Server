package fc5.i5e1server.domain.util;

import fc5.i5e1server.domain.annual.AnnualRepository;
import fc5.i5e1server.domain.annual.AnnualService;
import fc5.i5e1server.domain.auth.util.SecurityUtil;
import fc5.i5e1server.domain.member.MemberRepository;
import fc5.i5e1server.domain.model.Annual;
import fc5.i5e1server.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceUtil {

    private final MemberRepository memberRepository;
    private final AnnualRepository annualRepository;


    @Transactional
    public Member findByUserId(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 유저입니다."));
    }

    @Transactional
    public Long getUserId(){
        return SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 없음"));
    }

    public boolean isDuplicatedAnnual(LocalDate tempStartDate, LocalDate tempEndDate) {
        List<Annual> annuals = annualRepository.findByMemberId(getUserId());

        for (Annual annual : annuals) {
            if ((tempStartDate.isEqual(annual.getStartDate()) || tempStartDate.isAfter(annual.getStartDate())) &&
                    (tempStartDate.isEqual(annual.getEndDate()) || tempStartDate.isBefore(annual.getEndDate()))) {
                return true;
            }
            if ((tempEndDate.isEqual(annual.getStartDate()) || tempEndDate.isAfter(annual.getStartDate())) &&
                    (tempEndDate.isEqual(annual.getEndDate()) || tempEndDate.isBefore(annual.getEndDate()))) {
                return true;
            }
            if ((annual.getStartDate().isEqual(tempStartDate) || annual.getStartDate().isAfter(tempStartDate)) &&
                    (annual.getStartDate().isEqual(tempEndDate) || annual.getStartDate().isBefore(tempEndDate))) {
                return true;
            }
            if ((annual.getEndDate().isEqual(tempStartDate) || annual.getEndDate().isAfter(tempStartDate)) &&
                    (annual.getEndDate().isEqual(tempEndDate) || annual.getEndDate().isBefore(tempEndDate))) {
                return true;
            }
        }
        return false;
    }
}
