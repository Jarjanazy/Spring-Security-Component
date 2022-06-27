package codeit.components.springsecurityfeaturetests;

import codeit.security.SpringSecurity;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources")
@CucumberContextConfiguration
@SpringBootTest(classes = SpringSecurity.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberIntegrationTest {
    // entry point of our tests
}

