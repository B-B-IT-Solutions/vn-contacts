package cz.prm.business.users;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.business.BusinessComponentTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

@Slf4j
public class UserComponentTest extends BusinessComponentTestBase {

   @LocalServerPort
   int randomServerPort;

   @Test
   void getUsersTest() {
      log.error("ASSIGNED_PORT- {}", randomServerPort);
//      var users = getUsers();
//      assertThat(users).isNotEmpty().hasSize(2);
   }
}
