package cz.prm.business.user;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.assertions.UserComponentTestAssertions.assertUser;

import org.junit.jupiter.api.Test;

public class UserComponentTest extends UserComponentTestBase {

   @Test
   void getCurrentUser() {
      var user = user1GetCurrentUser();
      assertUser(user, USER_1);

      user = user2GetCurrentUser();
      assertUser(user, USER_2);

      user = user3GetCurrentUser();
      assertUser(user, USER_3);
   }
}
