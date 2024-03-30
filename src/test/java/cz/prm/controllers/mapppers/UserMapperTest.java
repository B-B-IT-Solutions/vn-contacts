package cz.prm.controllers.mapppers;

import static cz.prm.utils.UserUtils.user;
import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUser;
import static cz.prm.utils.assertions.UserAssertions.assertUsersDto;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class UserMapperTest {

   private UserMapper mapper = MapperUtils.getUserMapper();

   @Test
   void toUsersDto() {
      var users = users();
      var dtos = mapper.toUsersDto(users);
      assertUsersDto(users, dtos);
   }

   @Test
   void toUserDto() {
      var user = user();
      var dto = mapper.toUserDto(user);
      assertUser(user, dto);
   }
}