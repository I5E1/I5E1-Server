package fc5.i5e1server.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fc5.i5e1server.domain.annual.AnnualCreateReqDTO;
import fc5.i5e1server.domain.annual.AnnualUpdateReqDTO;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Entity
@DynamicInsert
public class Annual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Member member;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'REQUESTED'")
    private Status status;

    @Column(nullable = false, length = 20)
    private String summary;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Integer spentDays;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateByReq(AnnualCreateReqDTO annualCreateReqDTO) {
        this.startDate = annualCreateReqDTO.getStartDate();
        this.endDate = annualCreateReqDTO.getEndDate();
        this.summary = annualCreateReqDTO.getSummary();
        this.reason = annualCreateReqDTO.getReason();
        this.status = Status.REQUESTED;
        this.spentDays = (int) ChronoUnit.DAYS.between(annualCreateReqDTO.getStartDate(), annualCreateReqDTO.getEndDate()) + 1;
        this.createdAt = LocalDateTime.now();
    }
    public void addMember(Member member) {
        this.member = member;
        member.getAnnuals().add(this);
    }

    public void update(AnnualUpdateReqDTO annualUpdateReqDTO) {
        if (this.status != Status.REQUESTED) {
            throw new IllegalStateException("Only annuals in REQUESTED status can be updated");
        }
        this.startDate = annualUpdateReqDTO.getStartDate();
        this.endDate = annualUpdateReqDTO.getEndDate();
        this.summary = annualUpdateReqDTO.getSummary();
        this.reason = annualUpdateReqDTO.getReason();
        this.spentDays = (int) ChronoUnit.DAYS.between(annualUpdateReqDTO.getStartDate(), annualUpdateReqDTO.getEndDate()) + 1;
    }

    public void cancel() {
        if (this.status != Status.REQUESTED) {
            throw new IllegalStateException("Only annuals in REQUESTED status can be cancelled");
        }
        this.status = Status.CANCELED;
        this.member.increaseAnnualCount(this.spentDays);
    }
}
