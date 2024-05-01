package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ContactComponentTestUtils.contactDto;
import static cz.prm.utils.ContactComponentTestUtils.contactsQueryDto;
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
   void updateContact() {
      var contact = createContact(USER_1);
      var contactId = contact.getContactId();
      var updateDto = user1GetContact(contactId);

      updateDto.setEmail(uuid());
      updateDto.setFirstName(uuid());
      updateDto.setLastName(uuid());
      user1UpdateContact(contactId, updateDto);
      contact = getContactFromDb(updateDto);
      assertContact(contact, updateDto);

      user2UpdateContactExpectNotFound(contactId, updateDto);
      user3UpdateContactExpectNotFound(contactId, updateDto);

      contact = createContact(USER_2);
      contactId = contact.getContactId();
      updateDto = user2GetContact(contactId);

      updateDto.setEmail(uuid());
      updateDto.setFirstName(uuid());
      updateDto.setLastName(uuid());
      user2UpdateContact(contactId, updateDto);
      contact = getContactFromDb(updateDto);
      assertContact(contact, updateDto);

      user1UpdateContactExpectNotFound(contactId, updateDto);
      user3UpdateContactExpectNotFound(contactId, updateDto);

      contact = createContact(USER_3);
      contactId = contact.getContactId();
      updateDto = user3GetContact(contactId);

      updateDto.setEmail(uuid());
      updateDto.setFirstName(uuid());
      updateDto.setLastName(uuid());
      user3UpdateContact(contactId, updateDto);
      contact = getContactFromDb(updateDto);
      assertContact(contact, updateDto);

      user1UpdateContactExpectNotFound(contactId, updateDto);
      user2UpdateContactExpectNotFound(contactId, updateDto);
   }

   @Test
   void getContactsDataAccess() {
      var queryDto = contactsQueryDto();
      var pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      pageDto = user2GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      pageDto = user3GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      var user1Contacts = createContacts(USER_1);
      pageDto = user1GetContacts(queryDto);
      assertContacts(user1Contacts, pageDto);

      pageDto = user2GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      pageDto = user3GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      var user2Contacts = createContacts(USER_2);
      pageDto = user2GetContacts(queryDto);
      assertContacts(user2Contacts, pageDto);

      pageDto = user1GetContacts(queryDto);
      assertContacts(user1Contacts, pageDto);

      pageDto = user3GetContacts(queryDto);
      assertThat(pageDto.getContent()).isEmpty();

      var user3Contacts = createContacts(USER_3);
      pageDto = user3GetContacts(queryDto);
      assertContacts(user3Contacts, pageDto);

      pageDto = user1GetContacts(queryDto);
      assertContacts(user1Contacts, pageDto);

      pageDto = user2GetContacts(queryDto);
      assertContacts(user2Contacts, pageDto);
   }

   @Test
   void getContactsPagination() {
      var queryDto = contactsQueryDto();
      var pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getTotalPages()).isZero();
      assertThat(pageDto.getTotalElements()).isZero();
      assertThat(pageDto.getPageSize()).isEqualTo(50);
      assertThat(pageDto.getContent()).isEmpty();

      createContacts(USER_1, 21);
      pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getTotalPages()).isEqualTo(1);
      assertThat(pageDto.getTotalElements()).isEqualTo(21);
      assertThat(pageDto.getPageSize()).isEqualTo(50);
      assertThat(pageDto.getContent()).hasSize(21);

      var pagination = queryDto.getPagination();
      pagination.setPageSize(5);
      pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getTotalPages()).isEqualTo(5);
      assertThat(pageDto.getTotalElements()).isEqualTo(21);
      assertThat(pageDto.getPageSize()).isEqualTo(5);
      assertThat(pageDto.getContent()).hasSize(5);

      pagination.setPageNumber(1);
      pagination.setPageSize(10);
      pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getTotalPages()).isEqualTo(3);
      assertThat(pageDto.getTotalElements()).isEqualTo(21);
      assertThat(pageDto.getPageSize()).isEqualTo(10);
      assertThat(pageDto.getContent()).hasSize(10);

      pagination.setPageNumber(2);
      pagination.setPageSize(10);
      pageDto = user1GetContacts(queryDto);
      assertThat(pageDto.getTotalPages()).isEqualTo(3);
      assertThat(pageDto.getTotalElements()).isEqualTo(21);
      assertThat(pageDto.getPageSize()).isEqualTo(10);
      assertThat(pageDto.getContent()).hasSize(1);
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
