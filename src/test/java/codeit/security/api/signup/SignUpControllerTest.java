package codeit.security.api.signup;

import codeit.security.api.signup.dto.SignUpRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SignUpController.class)
@ContextConfiguration(classes = {SignUpController.class})
@WithMockUser
public class SignUpControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SignUpApiService signUpApiService;

    @Test
    public void givenSignUpEndPointIsCalled_ThenSignUpServiceIsCalled() throws Exception
    {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO("testUser", "testPass", "email");
        mockMvc.perform(
                post("/signup").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestDTO))
        ).andExpect(status().isOk());

        verify(signUpApiService).signUp(any(SignUpRequestDTO.class));
    }

    @Test
    public void givenAnInvalidDto_WhenTryingToSignup_ThenReturnError() throws Exception
    {
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO
                .builder()
                .userName("testUser")
                .password("testPass")
                .build();

        mockMvc.perform(
                post("/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDTO))
        ).andExpect(status().isBadRequest());
    }

}
