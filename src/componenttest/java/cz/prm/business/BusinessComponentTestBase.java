package cz.prm.business;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.user.UserDto;
import io.restassured.common.mapper.TypeRef;
import java.util.List;

public class BusinessComponentTestBase extends ComponentTestBase {

   private static String USERS_URL = "users";

   protected List<UserDto> getUsers() {
      var typeRef = new TypeRef<List<UserDto>>() {
      };
      return getMany(USERS_URL, typeRef);
   }
}
