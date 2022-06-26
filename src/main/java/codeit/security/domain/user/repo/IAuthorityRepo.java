package codeit.security.domain.user.repo;

import codeit.security.domain.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepo extends JpaRepository<Authority, Integer>
{
    Authority findByRole(String role);
}
