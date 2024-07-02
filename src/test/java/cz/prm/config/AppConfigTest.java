package cz.prm.config;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.assertions.CommonAssertions.assertUser;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AppConfigTest {

    private AppConfig appConfig = new AppConfig();

    @Test
    void auditProvider() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var provider = appConfig.auditProvider();
            var optional = provider.getCurrentAuditor();
            var auditor = optional.get();
            assertUser(user, auditor);
        }
    }

    @Test
    void objectMapper() {
        var mapper = appConfig.objectMapper();
        assertThat(mapper.getRegisteredModuleIds()).containsExactly("jackson-datatype-jsr310");
        assertThat(mapper.isEnabled(WRITE_DATES_AS_TIMESTAMPS)).isFalse();
    }
}