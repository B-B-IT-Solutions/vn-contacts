package cz.prm.config;

import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.assertions.CommonAssertions.assertUser;

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
}