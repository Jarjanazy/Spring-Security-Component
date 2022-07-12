package codeit.security.api.security.service;

import codeit.security.api.common.response.Response;
import codeit.security.api.security.dto.AuthenticationRequest;
import codeit.security.api.security.dto.AuthenticationResponse;
import codeit.security.api.security.dto.SignOutRequestDto;
import codeit.security.api.security.refreshtoken.RefreshTokenService;
import codeit.security.domain.user.entity.User;
import codeit.security.domain.user.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static codeit.security.api.common.response.ResponseFactory.createResponse;
import static codeit.security.api.common.response.ResponseFactory.createResponseWithBody;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class AuthenticationApiService
{
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailService;
    private final IUserRepo userRepo;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<Response> signout(SignOutRequestDto signOutRequestDto)
    {
        refreshTokenService.deleteByValue(signOutRequestDto.getRefreshToken());
        return createResponse("Refresh Token is Deleted", HttpStatus.OK);
    }

    public ResponseEntity<Response> verifyAndCreateAuthToken(AuthenticationRequest authenticationRequest)
    {
        try{

            verifyAuthenticationRequest(authenticationRequest);

            return createResponseWithBody(
                    "New Refresh Token is Created",
                    HttpStatus.CREATED,
                    createAuthToken(authenticationRequest));

        }catch (AuthenticationException e){
            return createResponse("Wrong Username or Password", UNAUTHORIZED);

        }catch (EntityNotFoundException re){
            return createResponse(String.format("The username %s isn't found", authenticationRequest.getUserName()), BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> createAccessTokenFromRefreshToken(HttpServletRequest servletRequest)
    {
        Optional<String> refreshTokenOptional = jwtService.extractJWTFromAuthorizationHeader(servletRequest);
        if (refreshTokenOptional.isEmpty())
            return createResponse("The refresh token doesn't exist", BAD_REQUEST);

        String refreshToken = refreshTokenOptional.get();
        if (jwtService.tokenIsValid(refreshToken) && refreshTokenService.refreshTokenExists(refreshToken)){
            return createResponseWithBody(
                    "New Access Token is Created",
                    HttpStatus.CREATED,
                    createAccessTokenFromRefreshToken(refreshToken));
        }
        else
            return createResponse("The refresh token is invalid", BAD_REQUEST);
    }

    private AuthenticationResponse createAccessTokenFromRefreshToken(String refreshToken)
    {
        CustomUserDetails userDetails = CustomUserDetails.builder().userName(jwtService.extractUsername(refreshToken)).build();
        String newAccessToken = createAccessToken(userDetails);
        return new AuthenticationResponse(newAccessToken);
    }

    private AuthenticationResponse createAuthToken(AuthenticationRequest authenticationRequest) {
        UserDetails userDetails =  customUserDetailService.loadUserByUsername(authenticationRequest.getUserName());

        User user = userRepo.
                findByUserName(userDetails.getUsername()).
                orElseThrow(EntityNotFoundException::new);

        String refreshToken = jwtService.createToken(userDetails, Optional.empty());

        refreshTokenService.saveNewRefreshToken(refreshToken, user);

        return new AuthenticationResponse(refreshToken, createAccessToken(userDetails), user.getUserName());
    }

    // if this method runs successfully it means that authentication done successfully
    private void verifyAuthenticationRequest(AuthenticationRequest ar) throws AuthenticationException
    {
        var authentication = new UsernamePasswordAuthenticationToken(ar.getUserName(), ar.getPassword());
        authenticationManager.authenticate(authentication);
    }

    private String createAccessToken(UserDetails userDetails)
    {
        return jwtService.createToken(userDetails, Optional.of(1000 * 60 * 30));
    }
}
