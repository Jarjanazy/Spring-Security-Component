package codeit.security.api.security.dto;

import codeit.security.api.common.response.IResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthenticationIResponse implements IResponse
{
    private String refreshToken;
    private String accessToken;
    private String userName;

    public AuthenticationIResponse(String accessToken)
    {
        this.accessToken = accessToken;
    }
}
