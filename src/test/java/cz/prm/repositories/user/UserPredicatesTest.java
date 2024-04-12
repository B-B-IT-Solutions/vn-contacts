package cz.prm.repositories.user;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPredicatesTest {

   private UserPredicates predicates;

   @BeforeEach
   void setUp() {
      predicates = new UserPredicates();
   }

   @Test
   void byUserId() {
      var query = predicates.byUseId(11L);
      assertThat(query).hasToString("user.userId = 11");
   }

   @Test
   void byEmail() {
      var email = uuid();
      var query = predicates.byEmail(email);
      var expectedString = format("user.email = %s", email);
      assertThat(query).hasToString(expectedString);
   }
}