package cz.prm.repositories.user;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactPredicatesTest {

   private ContactPredicates predicates;

   @BeforeEach
   void setUp() {
      predicates = new ContactPredicates();
   }

   @Test
   void byContactId() {
      var query = predicates.byContactId(11L);
      assertThat(query).hasToString("contact.userId = 11");
   }

   @Test
   void byEmail() {
      var email = uuid();
      var query = predicates.byEmail(email);
      var expectedString = format("contact.email = %s", email);
      assertThat(query).hasToString(expectedString);
   }
}