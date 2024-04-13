package cz.prm.business.contacts;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.business.BusinessComponentTestBase;
import org.junit.jupiter.api.Test;

public class ContactComponentTest extends BusinessComponentTestBase {

   @Test
   void getContactsTest() {
      var contacts = getContacts();
      assertThat(contacts).hasSize(2);
   }
}
