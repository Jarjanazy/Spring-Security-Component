package codeit.components.springsecurityfeaturetests.glue;


import codeit.components.springsecurityfeaturetests.exception.FeatureTestException;
import codeit.security.api.common.response.Response;
import codeit.security.api.security.dto.AuthenticationRequest;
import codeit.security.api.signup.dto.SignUpRequestDTO;
import codeit.security.domain.user.entity.User;
import codeit.security.domain.user.repo.IUserRepo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;


public class StepDefinition {
    String port;

    @Autowired
    IUserRepo userRepo;

    public StepDefinition(Environment environment) {
        this.port = environment.getProperty("local.server.port");
    }

    @Given("a fresh user with email {word} and username {word}")
    public void freshUserWithEmail(String email, String userName) {
        Optional<User> byEmail = userRepo.findByEmail(email);
        Optional<User> byUserName = userRepo.findByUserName(userName);

        if(byEmail.isPresent())
            throw new FeatureTestException(format("User with email %s already exists", email));

        if(byUserName.isPresent())
            throw new FeatureTestException(format("User with username %s already exists", userName));
    }

    @When("user signs-up with email {word} and username {word} and password {word}")
    public void signupWithEmailUserNameAndPassword(String email, String username, String password) {
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO
                .builder()
                .email(email)
                .userName(username)
                .password(password)
                .build();

        ResponseEntity<Response> responseEntity =
                new RestTemplate().postForEntity(format("http://localhost:%s/signup", port), signUpRequestDTO, Response.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Then("user with username {word} and password {word} is able to sign-in")
    public void signinWithEmailAndPassword(String username, String password) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username, password);

        ResponseEntity<Response> responseEntity =
                new RestTemplate().postForEntity(format("http://localhost:%s/authenticate", port), authenticationRequest, Response.class);



    }


}
