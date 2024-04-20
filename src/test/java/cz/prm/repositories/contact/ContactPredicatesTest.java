package cz.prm.repositories.contact;

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
   void contacts() {
      var username = uuid();
      var query = predicates.contacts(username);
      var expectedString = format("contact.owner = %s", username);
      assertThat(query).hasToString(expectedString);
   }

   @Test
   void byContactId() {
      var query = predicates.byContactId(11L);
      assertThat(query).hasToString("contact.contactId = 11");
   }

}