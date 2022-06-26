package codeit.security.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerTest
{
    @Autowired
    MockMvc mockMvc;

    @Test
    public void givenGETRequestToSwagger_Return200() throws Exception
    {
        mockMvc.perform(get("/swagger-ui.html#")).andExpect(status().isOk());
    }
}
