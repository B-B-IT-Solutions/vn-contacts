package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ContactComponentTestUtils.contactDto;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContact;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContacts;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

   @Test
   void createContact() {
      var toCreateDto = contactDto();
      user1CreateContact(toCreateDto);
      var contact = getContactFromDb(toCreateDto);
      var contactId = contact.getContactId();

      var createdDto = user1GetContact(contactId);
      assertContact(contact, createdDto);
      user2GetContactExpectNotFound(contactId);
      user3GetContactExpectNotFound(contactId);

      toCreateDto = contactDto();
      user2CreateContact(toCreateDto);
      contact = getContactFromDb(toCreateDto);
      contactId = contact.getContactId();

      createdDto = user2GetContact(contactId);
      assertContact(contact, createdDto);
      user1GetContactExpectNotFound(contactId);
      user3GetContactExpectNotFound(contactId);

      toCreateDto = contactDto();
      user3CreateContact(toCreateDto);
      contact = getContactFromDb(toCreateDto);
      contactId = contact.getContactId();

      createdDto = user3GetContact(contactId);
      assertContact(contact, createdDto);
      user1GetContactExpectNotFound(contactId);
      user2GetContactExpectNotFound(contactId);
   }

   @Test
   void getContacts() {
      var contactsDto = user1GetContacts();
      assertThat(contactsDto).isEmpty();

      contactsDto = user2GetContacts();
      assertThat(contactsDto).isEmpty();

      contactsDto = user3GetContacts();
      assertThat(contactsDto).isEmpty();

      var user1Contacts = createContacts(USER_1);
      contactsDto = user1GetContacts();
      assertContacts(user1Contacts, contactsDto);

      contactsDto = user2GetContacts();
      assertThat(contactsDto).isEmpty();

      contactsDto = user3GetContacts();
      assertThat(contactsDto).isEmpty();

      var user2Contacts = createContacts(USER_2);
      contactsDto = user2GetContacts();
      assertContacts(user2Contacts, contactsDto);

      contactsDto = user1GetContacts();
      assertContacts(user1Contacts, contactsDto);

      contactsDto = user3GetContacts();
      assertThat(contactsDto).isEmpty();

      var user3Contacts = createContacts(USER_3);
      contactsDto = user3GetContacts();
      assertContacts(user3Contacts, contactsDto);

      contactsDto = user1GetContacts();
      assertContacts(user1Contacts, contactsDto);

      contactsDto = user2GetContacts();
      assertContacts(user2Contacts, contactsDto);

   }

   @Test
   void getContact() {
      var contact = createContact(USER_1);
      var contactId = contact.getContactId();

      var contactDto = user1GetContact(contactId);
      assertContact(contact, contactDto);
      user2GetContactExpectNotFound(contactId);
      user3GetContactExpectNotFound(contactId);

      contact = createContact(USER_2);
      contactId = contact.getContactId();
      contactDto = user2GetContact(contactId);
      assertContact(contact, contactDto);
      user1GetContactExpectNotFound(contactId);
      user3GetContactExpectNotFound(contactId);

      contact = createContact(USER_3);
      contactId = contact.getContactId();
      contactDto = user3GetContact(contactId);
      assertContact(contact, contactDto);
      user1GetContactExpectNotFound(contactId);
      user2GetContactExpectNotFound(contactId);
   }

}
