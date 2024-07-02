package cz.prm.repositories.settings;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class UserSettingsPredicatesTest {

    private SettingsPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new SettingsPredicates();
    }

    @Test
    void byContactId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.userSettings();
            var expectedString = format("userSettings.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}