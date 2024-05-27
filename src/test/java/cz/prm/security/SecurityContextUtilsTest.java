package cz.prm.security;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class SecurityContextUtilsTest {

    private static final String SUB_CLAIM = "sub";
    private static final String EMAIL_CLAIM = "email";

    @Mock
    private SecurityContext context;
    @Mock
    private JwtAuthenticationToken authentication;
    @Mock
    private Jwt jwt;

    @Test
    void getUser() {
        try (MockedStatic<SecurityContextHolder> contextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            var username = uuid();
            var email = uuid();

            when(jwt.getClaimAsString(SUB_CLAIM)).thenReturn(username);
            when(jwt.getClaimAsString(EMAIL_CLAIM)).thenReturn(email);
            when(context.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(jwt);
            contextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(context);

            var result = SecurityContextUtils.getUser();
            assertThat(result.getUsername()).isEqualTo(username);
            assertThat(result.getEmail()).isEqualTo(email);
        }
    }
}