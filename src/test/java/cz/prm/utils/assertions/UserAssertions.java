package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.User;
import java.util.List;
import java.util.Objects;

public class UserAssertions {

   public static void assertUsers(List<User> users1, List<User> users2) {
      assertThat(users1).isNotEmpty().hasSameSizeAs(users2);
      users1.forEach(u1 -> {
         var u2 = users2.stream().filter(u -> Objects.equals(u1.getUserId(), u.getUserId())).findFirst().get();
         assertUser(u1, u2);
      });
   }

   public static void assertUser(User user1, User user2) {
      assertThat(user1.getUserId()).isEqualTo(user2.getUserId());
      assertThat(user1.getFirstName()).isEqualTo(user2.getFirstName());
      assertThat(user1.getLastName()).isEqualTo(user2.getLastName());
   }
}
