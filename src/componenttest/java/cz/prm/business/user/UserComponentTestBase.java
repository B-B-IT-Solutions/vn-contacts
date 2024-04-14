package cz.prm.business.user;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.user.UserDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class UserComponentTestBase extends ComponentTestBase {

   private static String USERS_URL = "users";
   private static String CURRENT_USER_URL = USERS_URL + "/current-user";

   protected UserDto user1GetCurrentUser() {
      return getCurrentUser(USER_1);
   }

   protected UserDto user2GetCurrentUser() {
      return getCurrentUser(USER_2);
   }

   protected UserDto user3GetCurrentUser() {
      return getCurrentUser(USER_3);
   }

   protected UserDto getCurrentUser(ComponentTestUser user) {
      var typeRef = new TypeRef<UserDto>() {
      };
      return getOne(CURRENT_USER_URL, user, typeRef);
   }
}
