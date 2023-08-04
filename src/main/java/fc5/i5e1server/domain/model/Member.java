package fc5.i5e1server.domain.model;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
