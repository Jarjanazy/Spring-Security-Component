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
    private Object body;

    public Response(String message, HttpStatus httpStatus)
    {
        this.message = message;
        this.code = httpStatus.value();
    }

    public Response(String message, HttpStatus httpStatus, Object body) {
        this.message = message;
        this.code = httpStatus.value();
        this.body = body;
    }
}
