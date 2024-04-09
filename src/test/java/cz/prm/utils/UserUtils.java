package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;

import cz.prm.domain.User;
import java.util.List;
import org.assertj.core.util.Lists;

public class UserUtils {

   public static List<User> users() {
      return Lists.newArrayList(user(), user(), user());
   }

   public static User user() {
      var user = new User();
      user.setUserId(randomLong());
      user.setFirstName(uuid());
      user.setLastName(uuid());
      return user;
   }

}
