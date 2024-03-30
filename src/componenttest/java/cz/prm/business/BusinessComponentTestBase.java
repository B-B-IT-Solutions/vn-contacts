package cz.prm.business;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.UserDto;
import io.restassured.mapper.TypeRef;
import java.util.List;

public class BusinessComponentTestBase extends ComponentTestBase {

   private static String USERS_URL = "users";

   protected List<UserDto> getUsers() {
      var typeRef = new TypeRef<List<UserDto>>() {
      };
      return getMany(USERS_URL, typeRef);
   }
}
