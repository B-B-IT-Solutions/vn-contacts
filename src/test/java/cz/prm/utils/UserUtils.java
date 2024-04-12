package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.domain.user.User;
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
      user.setEmail(uuid());
      user.setPassword(uuid());
      user.setEmailVerifiedAt(now());
      return user;
   }

}
