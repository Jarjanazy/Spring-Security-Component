package codeit.security.domain.user.repo;

import codeit.security.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer>
{
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);
}
