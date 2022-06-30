package codeit.security.api.security.controller;

import codeit.security.api.common.response.Response;
import codeit.security.api.security.dto.AuthenticationRequest;
import codeit.security.api.security.dto.SignOutRequestDto;
import codeit.security.api.security.service.AuthenticationApiService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Getter
public class AuthenticationController {
    private final AuthenticationApiService authenticationApiService;

    @PostMapping("/authenticate")
    public ResponseEntity<Response> verifyAndCreateAuthToken(@RequestBody AuthenticationRequest authenticationRequest)
    {
        return authenticationApiService.verifyAndCreateAuthToken(authenticationRequest);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Response> createAccessTokenFromRefreshToken(HttpServletRequest servletRequest)
    {
        return authenticationApiService.createAccessTokenFromRefreshToken(servletRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signout(@RequestBody SignOutRequestDto signOutRequestDto)
    {
        return authenticationApiService.signout(signOutRequestDto);
    }

}
