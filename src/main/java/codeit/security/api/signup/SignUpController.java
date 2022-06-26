package codeit.security.api.signup;


import codeit.security.api.common.response.IResponse;
import codeit.security.api.signup.dto.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApiService signUpApiService;

    @PostMapping("/signup")
    public ResponseEntity<IResponse> signUp(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO)
    {
        return signUpApiService.signUp(signUpRequestDTO);
    }
}
