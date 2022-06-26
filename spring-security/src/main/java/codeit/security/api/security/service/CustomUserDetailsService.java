package codeit.security.api.security.service;

import codeit.security.domain.user.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
    private final IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        return userRepo
                .findByUserName(userName)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s wasn't found", userName)));
    }
}
