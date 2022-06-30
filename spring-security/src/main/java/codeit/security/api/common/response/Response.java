package codeit.security.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Response {
    private String message;
    private Integer code;

    public Response(String message, HttpStatus httpStatus)
    {
        this.message = message;
        this.code = httpStatus.value();
    }
}
