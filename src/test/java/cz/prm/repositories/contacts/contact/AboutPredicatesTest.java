package cz.prm.repositories.contacts.contact;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AboutPredicatesTest {

    private AboutPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new AboutPredicates();
    }

    @Test
    void byContactId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byContactId(15L);
            var expectedString = format("about.owner.username = %s && about.contactId = 15", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}