package cz.prm.security;

import static cz.prm.utils.UserUtils.user;
import static cz.prm.utils.assertions.UserAssertions.assertUserDetails;

import org.junit.jupiter.api.Test;

class PrmUserDetailsTest {

   @Test
   void newInstance() {
      var user = user();
      var userDetails = new PrmUserDetails(user);
      assertUserDetails(user, userDetails);
   }

}