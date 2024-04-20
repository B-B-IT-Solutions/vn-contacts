package cz.prm.business.contacts;

import static cz.prm.utils.SecurityContextComponentTestUtils.clearContext;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUser1Context;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContacts;

import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

   @Test
   void getContacts() {
      ensureUser1Context();
      var contacts = contactService.getContacts();
      clearContext();
      var contactsDto = user1GetContacts();
      assertContacts(contacts, contactsDto);

      contactsDto = user2GetContacts();
      assertContacts(contacts, contactsDto);

      contactsDto = user3GetContacts();
      assertContacts(contacts, contactsDto);
   }
}
