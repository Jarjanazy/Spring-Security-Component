package codeit.security.api.factory;

import codeit.security.api.common.response.Response;
import codeit.security.api.common.response.ResponseFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class IResponseFactoryTest
{

    @Test
    public void givenMessage_ThenCreateBadRequestResponse()
    {
        ResponseEntity<Response> response = ResponseFactory.createResponse("baad", HttpStatus.BAD_REQUEST);

        Response errorResponse = response.getBody();

        assertThat(errorResponse.getMessage()).isEqualTo("baad");
        assertThat(errorResponse.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
