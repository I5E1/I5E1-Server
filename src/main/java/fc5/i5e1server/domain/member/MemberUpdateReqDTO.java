package fc5.i5e1server.domain.member;

import lombok.Getter;

@Getter
public class MemberUpdateReqDTO {
    private String tel;
    private String password;

    public boolean isTelUpdated() {
        return tel != null && !tel.trim().isEmpty();
    }

    public boolean isPasswordUpdated() {
        return password != null && !password.trim().isEmpty();
    }
}