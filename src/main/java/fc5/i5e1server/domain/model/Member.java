package fc5.i5e1server.domain.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 12)
    private String tel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'STAFF'")
    private Position position;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    @ColumnDefault(value = "15")
    private int annualCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateTel(String tel) {
        this.tel = tel;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Annual> annuals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Duty> duty = new ArrayList<>();

    public void reduceAnnualCount(int spentDays) {
        this.annualCount -= spentDays;
    }

    public void increaseAnnualCount(int spentDays) {
        this.annualCount += spentDays;
    }
}