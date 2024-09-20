package cz.prm.domain.common;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private static final String UPN_CLAIM = "upn";
    private static final String EMAIL_CLAIM = "email";

    @Mock
    private Jwt jwt;

    @Test
    void newInstance() {
        var username = uuid();
        var email = uuid();
        when(jwt.getClaimAsString(UPN_CLAIM)).thenReturn(username);
        when(jwt.getClaimAsString(EMAIL_CLAIM)).thenReturn(email);

        var user = new User(jwt);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}