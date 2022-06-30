package codeit.security.api.security.dto;

import codeit.security.api.common.response.Response;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthenticationResponse extends Response {
    private String refreshToken;
    private String accessToken;
    private String userName;

    public AuthenticationResponse(String accessToken)
    {
        this.accessToken = accessToken;
    }
}
