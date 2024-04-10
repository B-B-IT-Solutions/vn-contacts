package cz.prm.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppConfigTest {

   private AppConfig appConfig = new AppConfig();

   @Test
   void auditProvider() {
      var provider = appConfig.auditProvider();
      var optional = provider.getCurrentAuditor();
      var auditor = optional.get();
      assertThat(auditor.getEmail()).isEqualTo("emai@email.com");
   }
}