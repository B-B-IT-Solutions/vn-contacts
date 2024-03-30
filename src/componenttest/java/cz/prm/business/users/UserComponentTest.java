package cz.prm.business.users;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.business.BusinessComponentTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UserComponentTest extends BusinessComponentTestBase {

   @Test
   void getUsersTest() {
      var users = getUsers();
      assertThat(users).isNotEmpty().hasSize(0);
   }
}
