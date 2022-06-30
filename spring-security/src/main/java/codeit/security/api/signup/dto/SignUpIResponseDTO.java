package codeit.security.api.signup.dto;

import codeit.security.api.common.response.Response;
import codeit.security.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpIResponseDTO extends Response {
    private String userName;

    public static SignUpIResponseDTO fromUser(User user)
    {
        return new SignUpIResponseDTO(user.getUserName());
    }
}
