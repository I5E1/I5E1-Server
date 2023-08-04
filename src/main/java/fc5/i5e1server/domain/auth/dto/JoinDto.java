package fc5.i5e1server.domain.auth.dto;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class JoinDto {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String tel;
}
