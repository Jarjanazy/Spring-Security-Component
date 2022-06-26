package codeit.security.domain.seeder;

import codeit.security.domain.user.entity.Authority;
import codeit.security.domain.user.repo.IAuthorityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DBSeeder implements CommandLineRunner
{

    private final IAuthorityRepo authorityRepo;

    @Override
    public void run(String... args)
    {
        Authority authority = Authority.builder().role("ROLE_USER").build();

        authorityRepo.save(authority);
    }
}
