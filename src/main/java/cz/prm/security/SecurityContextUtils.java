package cz.prm.security;

import static lombok.AccessLevel.PRIVATE;

import cz.prm.domain.common.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@NoArgsConstructor(access = PRIVATE)
public class SecurityContextUtils {

    public static User getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return new User((Jwt) authentication.getPrincipal());
    }
}
