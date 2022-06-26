package codeit.security.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "APP_USER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name="PASS_WORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @ManyToOne
    private Authority authority;

    @Column(name = "IS_ENABLED", nullable = false)
    private boolean isEnabled;

}
