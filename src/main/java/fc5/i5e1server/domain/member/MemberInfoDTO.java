package fc5.i5e1server.domain.member;

import fc5.i5e1server.domain.model.Member;
import fc5.i5e1server.domain.model.Position;
import lombok.Getter;

@Getter
public class MemberInfoDTO {
    private String name;
    private String email;
    private String tel;
    private Position position;
    private int annualCount;

    public MemberInfoDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.tel = member.getTel();
        this.position = member.getPosition();
        this.annualCount = member.getAnnualCount();
    }
}