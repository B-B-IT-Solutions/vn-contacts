package cz.prm.business.contacts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

   @Test
   void getContactsTest() {
      var contacts = user1GetContacts();
      assertThat(contacts).hasSize(2);

      contacts = user2GetContacts();
      assertThat(contacts).hasSize(2);

      contacts = user3GetContacts();
      assertThat(contacts).hasSize(2);
   }
}
