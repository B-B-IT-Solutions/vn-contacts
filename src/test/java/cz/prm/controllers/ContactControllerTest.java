package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contactDto;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContacts;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.domain.contact.Contact;
import cz.prm.services.contact.ContactService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

   @Mock
   private ContactService contactService;
   @Captor
   private ArgumentCaptor<Contact> contactCapt;
   private ContactMapper mapper = MapperUtils.getContactMapper();

   private ContactController controller;

   @BeforeEach
   void setUp() {
      controller = new ContactController(contactService, mapper);
   }

   @Test
   void createContact() {
      var dto = contactDto();
      controller.createContact(dto);
      verify(contactService).createContact(contactCapt.capture());
      var contact = contactCapt.getValue();
      assertContact(contact, dto);
   }

   @Test
   void updateContact() {
      var dto = contactDto();
      controller.updateContact(dto.getContactId(), dto);
      verify(contactService).updateContact(eq(dto.getContactId()), contactCapt.capture());
      var contact = contactCapt.getValue();
      assertContact(contact, dto);
   }

   @Test
   void getContacts() {
      var page = page(contacts());
      when(contactService.getContacts(null)).thenReturn(page);
      var result = controller.getContacts();
      assertContacts(page, result);
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