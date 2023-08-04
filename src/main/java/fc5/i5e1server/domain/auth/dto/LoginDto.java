package fc5.i5e1server.domain.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDto {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
