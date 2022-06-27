package codeit.components.springsecurityfeaturetests;

import codeit.security.SpringSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = SpringSecurity.class)
class SpringSecurityFeatureTestsApplicationTests {

	@Test
	void contextLoads() {
	}

}
