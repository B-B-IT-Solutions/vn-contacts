package cz.prm.repositories.customizations;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CustomizationsPredicatesTest {

    private CustomizationsPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new CustomizationsPredicates();
    }

    @Test
    void contactSettings() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.contactSettings();
            var expectedString = format("contactCustomizations.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void noteSettings() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.noteSettings();
            var expectedString = format("noteCustomizations.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}