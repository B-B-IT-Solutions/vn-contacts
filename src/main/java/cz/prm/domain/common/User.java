package cz.prm.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final String SUB_CLAIM = "sub";
    public static final String EMAIL_CLAIM = "email";

    @Column(name = "USERNAME", insertable = false, updatable = false)
    private String username;

    @Column(name = "EMAIL", insertable = false, updatable = false)
    private String email;

    public User(Jwt jwt) {
        this.username = jwt.getClaimAsString(SUB_CLAIM);
        this.email = jwt.getClaimAsString(EMAIL_CLAIM);
    }
}
