package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUser1Context;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUser3Context;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContact;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContacts;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

   @Test
   void getContacts() {
      createContacts(USER_1);
      createContacts(USER_3);

      ensureUser1Context();
      var user1Contacts = contactService.getContacts();
      ensureUser3Context();
      var user3Contacts = contactService.getContacts();
      assertThat(user1Contacts).doesNotContainAnyElementsOf(user3Contacts);

      var contactsDto = user1GetContacts();
      assertContacts(user1Contacts, contactsDto);

      contactsDto = user2GetContacts();
      assertThat(contactsDto).isEmpty();

      contactsDto = user3GetContacts();
      assertContacts(user3Contacts, contactsDto);
   }

   @Test
   void getContact() {
      var contact = createContact(USER_1);
      var contactDto = user1GetContact(contact.getContactId());
      assertContact(contact, contactDto);

      contact = createContact(USER_2);
      contactDto = user2GetContact(contact.getContactId());
      assertContact(contact, contactDto);

      contact = createContact(USER_3);
      contactDto = user3GetContact(contact.getContactId());
      assertContact(contact, contactDto);
   }
}
