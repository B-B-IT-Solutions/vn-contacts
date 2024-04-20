package cz.prm.business.contacts;

import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContacts;

import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

   @Test
   void getContacts() {
      var contacts = contactService.getContacts();
      var contactsDto = user1GetContacts();
      assertContacts(contacts, contactsDto);

      contactsDto = user2GetContacts();
      assertContacts(contacts, contactsDto);

      contactsDto = user3GetContacts();
      assertContacts(contacts, contactsDto);
   }
}
