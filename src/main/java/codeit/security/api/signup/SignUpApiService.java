package codeit.security.api.signup;

import codeit.security.api.common.response.IResponse;
import codeit.security.api.signup.dto.SignUpRequestDTO;
import codeit.security.api.signup.dto.SignUpIResponseDTO;
import codeit.security.domain.user.entity.User;
import codeit.security.domain.user.repo.IAuthorityRepo;
import codeit.security.domain.user.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static codeit.security.api.common.response.ResponseFactory.createResponse;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class SignUpApiService
{
    private final IUserRepo userRepo;
    private final IAuthorityRepo authorityRepo;

    public ResponseEntity<IResponse> signUp(SignUpRequestDTO signUpRequestDTO) {
        return userRepo.findByUserName(signUpRequestDTO.getUserName()).
                map(this::createUsernameAlreadyTakenMessage).
                orElseGet(() -> createNewUser(signUpRequestDTO));
    }

    private ResponseEntity<IResponse> createUsernameAlreadyTakenMessage(User user) {
        return createResponse(String.format("The user name %s already exists", user.getUserName()), CONFLICT);
    }

    private ResponseEntity<IResponse> createNewUser(SignUpRequestDTO signUpRequestDTO) {
        User newUser = createUserFromSignUpDTO(signUpRequestDTO);

        userRepo.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignUpIResponseDTO.fromUser(newUser));
    }

    private User createUserFromSignUpDTO(SignUpRequestDTO signUpRequestDTO) {
        return User.
                builder().
                userName(signUpRequestDTO.getUserName()).
                password(signUpRequestDTO.getPassword()).
                email(signUpRequestDTO.getEmail()).
                isEnabled(true).
                authority(authorityRepo.findByRole("ROLE_USER")).
                build();
    }

}
