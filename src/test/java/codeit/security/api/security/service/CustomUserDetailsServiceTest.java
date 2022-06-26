package codeit.security.api.security.service;

import codeit.security.domain.user.entity.Authority;
import codeit.security.domain.user.entity.User;
import codeit.security.domain.user.repo.IUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomUserDetailsServiceTest
{
    @Mock
    private IUserRepo userRepo;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup()
    {
        customUserDetailsService = new CustomUserDetailsService(userRepo);
    }

    @Test
    public void givenUserName_WhenUserIsFound_ThenCreateCustomUserDetails()
    {
        Authority authority = Authority.builder().role("testRole").build();
        User user = User
                .builder()
                .authority(authority)
                .userName("userName")
                .isEnabled(true)
                .password("password").build();

        when(userRepo.findByUserName("userName")).thenReturn(Optional.of(user));

        UserDetails result = customUserDetailsService.loadUserByUsername("userName");

        GrantedAuthority grantedAuthority = result.getAuthorities().iterator().next();

        assertThat(grantedAuthority.getAuthority()).isEqualTo(authority.getRole());
        assertThat(result.getPassword()).isEqualTo("password");
        assertThat(result.getUsername()).isEqualTo("userName");
    }

    @Test
    public void givenUserName_WhenNotFound_ThenThrowUsernameNotFoundException()
    {
        when(userRepo.findByUserName("userName")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("userName"));
    }
}
