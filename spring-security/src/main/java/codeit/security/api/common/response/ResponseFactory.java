package codeit.security.api.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory
{

    public static ResponseEntity<Response> createResponse(String message, HttpStatus httpStatus)
    {
        return ResponseEntity
                .status(httpStatus)
                .body(new Response(message, httpStatus));
    }
    public static ResponseEntity<Response> createResponseWithBody(String message, HttpStatus httpStatus, Object body)
    {
        return ResponseEntity
                .status(httpStatus)
                .body(new Response(message, httpStatus, body));
    }
}
