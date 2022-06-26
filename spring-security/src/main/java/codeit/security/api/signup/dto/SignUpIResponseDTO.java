package codeit.security.api.signup.dto;

import codeit.security.api.common.response.IResponse;
import codeit.security.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpIResponseDTO implements IResponse
{
    private String userName;

    public static SignUpIResponseDTO fromUser(User user)
    {
        return new SignUpIResponseDTO(user.getUserName());
    }
}
