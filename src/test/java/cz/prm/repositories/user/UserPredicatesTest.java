package cz.prm.repositories.user;

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
}