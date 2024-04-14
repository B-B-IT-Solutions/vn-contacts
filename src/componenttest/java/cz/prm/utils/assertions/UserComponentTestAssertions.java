package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.user.UserDto;
import cz.prm.utils.ComponentTestUser;

public class UserComponentTestAssertions {

   public static void assertUser(UserDto userDto, ComponentTestUser user) {
      assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
   }
}
