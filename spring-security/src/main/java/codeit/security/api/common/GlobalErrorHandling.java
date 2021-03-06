package codeit.security.api.common;

import codeit.security.api.common.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static codeit.security.api.common.response.ResponseFactory.createResponse;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@ControllerAdvice
public class GlobalErrorHandling {
    private final Logger log = LoggerFactory.getLogger(GlobalErrorHandling.class);

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> internalServerErrorHandler(Exception e){
        log.error("An internal server error happened",e);
        return createResponse("An error happened in the API, please report the incident", INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> methodNotAllowedErrorHandler(HttpRequestMethodNotSupportedException e){
        log.error(String.format("An unsupported method call %s", e.getMessage()));
        return createResponse("An unsupported method call, please check the API docs", METHOD_NOT_ALLOWED);
    }
}
