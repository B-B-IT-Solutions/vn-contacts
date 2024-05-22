package cz.prm.security;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = PRIVATE)
public class SecurityContextUtils {

    public static String getUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
