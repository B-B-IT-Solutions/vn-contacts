package cz.prm.controllers;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactsDto;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.contact.ContactService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

   @Mock
   private ContactService contactService;
   private ContactMapper mapper = MapperUtils.getContactMapper();

   private ContactController controller;

   @BeforeEach
   void setUp() {
      controller = new ContactController(contactService, mapper);
   }

   @Test
   void getContacts() {
      var contacts = contacts();
      when(contactService.getContacts()).thenReturn(contacts);
      var result = controller.getContacts();
      assertContactsDto(contacts, result);
   }

   @Test
   void getContact() {
      var contact = contact();
      var contactId = contact.getContactId();
      when(contactService.getContact(contactId)).thenReturn(contact);
      var result = controller.getContact(contactId);
      assertContact(contact, result);
   }

}