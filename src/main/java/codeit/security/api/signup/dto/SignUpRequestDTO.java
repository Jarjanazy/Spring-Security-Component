package codeit.security.api.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO
{
    @NotBlank(message = "User name can't be empty")
    private String userName;
    @NotBlank(message = "Password can't be empty")
    private String password;
    @NotBlank(message = "Email can't be empty")
    private String email;
}
