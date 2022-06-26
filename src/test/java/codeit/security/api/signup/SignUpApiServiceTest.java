package codeit.security.api.signup;

import codeit.security.api.common.response.Response;
import codeit.security.api.common.response.IResponse;
import codeit.security.api.signup.dto.SignUpRequestDTO;
import codeit.security.api.signup.dto.SignUpIResponseDTO;
import codeit.security.domain.user.entity.Authority;
import codeit.security.domain.user.entity.User;
import codeit.security.domain.user.repo.IAuthorityRepo;
import codeit.security.domain.user.repo.IUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SignUpApiServiceTest
{
    @Mock
    IUserRepo userRepo;

    @Mock
    IAuthorityRepo authorityRepo;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    SignUpApiService signUpApiService;

    @BeforeEach
    public void setup()
    {
        signUpApiService = new SignUpApiService(userRepo, authorityRepo);
    }

    @Test
    public void givenAUserName_WhenAlreadyExists_ThenReturnConflict()
    {
        User userToBeReturned = User.builder().userName("userName").password("password").build();

        when(userRepo.findByUserName("userName")).thenReturn(Optional.of(userToBeReturned));

        ResponseEntity<IResponse> response = signUpApiService.signUp(new SignUpRequestDTO("userName", "password", "email"));

        Response errorResponse = (Response) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(errorResponse.getMessage()).isEqualTo("The user name userName already exists");
    }

    @Test
    public void givenAUserName_WhenDoesntExist_ThenCreateNewUser()
    {
        when(userRepo.findByUserName("userName")).thenReturn(Optional.empty());

        Authority authority = new Authority();
        when(authorityRepo.findByRole("ROLE_USER")).thenReturn(authority);

        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO("userName", "password", "email");

        ResponseEntity<IResponse> result = signUpApiService.signUp(signUpRequestDTO);

        SignUpIResponseDTO signUpResponseDTO = (SignUpIResponseDTO) result.getBody();

        verify(userRepo).save(userArgumentCaptor.capture());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(signUpResponseDTO.getUserName()).isEqualTo("userName");
        assertThat(userArgumentCaptor.getValue().isEnabled()).isTrue();
    }


}
