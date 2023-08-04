package fc5.i5e1server.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fc5.i5e1server.domain.duty.DutyCreateReqDTO;
import fc5.i5e1server.domain.duty.DutyUpdateReqDTO;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@DynamicInsert
public class Duty {

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
    private LocalDate dutyDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'REQUESTED'")
    private Status status;

    @Column(nullable = false)
    @ColumnDefault("'의무 당직'")
    private String reason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void addMember(Member member) {
        this.member = member;
        member.getDuty().add(this);
    }

    public void create(DutyCreateReqDTO dutyCreateReqDTO) {
        this.dutyDate = dutyCreateReqDTO.getDutyDate();
        this.reason = dutyCreateReqDTO.getReason();
        this.status = Status.REQUESTED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public void update(DutyUpdateReqDTO dutyUpdateReqDTO) {
        this.dutyDate = dutyUpdateReqDTO.getDutyDate();
        this.reason = dutyUpdateReqDTO.getReason();
        this.status = Status.REQUESTED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
