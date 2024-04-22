package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ContactComponentTestUtils.contact;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import cz.prm.repositories.contact.ContactRepository;
import cz.prm.services.contact.ContactService;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactComponentTestBase extends ComponentTestBase {

   protected static String CONTACTS_URL = "contacts";
   protected static String CONTACT_URL = CONTACTS_URL + "/%s";

   @Autowired
   protected ContactRepository contactRepository;
   @Autowired
   protected ContactService contactService;

   @BeforeEach
   void setUp() {
      contactRepository.deleteAll();
   }

   protected List<ContactDto> user1GetContacts() {
      return getContacts(USER_1);
   }

   protected List<ContactDto> user2GetContacts() {
      return getContacts(USER_2);
   }

   protected List<ContactDto> user3GetContacts() {
      return getContacts(USER_3);
   }

   protected ContactDto user1GetContact(Long contactId) {
      return getContact(contactId, USER_1);
   }

   protected ContactDto user2GetContact(Long contactId) {
      return getContact(contactId, USER_2);
   }

   protected ContactDto user3GetContact(Long contactId) {
      return getContact(contactId, USER_3);
   }

   protected void user1GetContactExpectNotFound(Long contactId) {
      getContactExpectNotFound(contactId, USER_1);
   }

   protected void user2GetContactExpectNotFound(Long contactId) {
      getContactExpectNotFound(contactId, USER_2);
   }

   protected void user3GetContactExpectNotFound(Long contactId) {
      getContactExpectNotFound(contactId, USER_3);
   }

   protected List<ContactDto> getContacts(ComponentTestUser user) {
      var typeRef = new TypeRef<List<ContactDto>>() {
      };
      return getMany(CONTACTS_URL, user, typeRef);
   }

   protected ContactDto getContact(Long contactId, ComponentTestUser user) {
      var url = format(CONTACT_URL, contactId);
      var typeRef = new TypeRef<ContactDto>() {
      };
      return getOne(url, user, typeRef);
   }

   protected void getContactExpectNotFound(Long contactId, ComponentTestUser user) {
      var url = format(CONTACT_URL, contactId);
      getExpectNotFount(url, user);
   }

   protected List<Contact> createContacts(ComponentTestUser user) {
      return createContacts(user, 3);
   }

   protected List<Contact> createContacts(ComponentTestUser user, int numOfContacts) {
      return IntStream.range(0, numOfContacts).mapToObj((i) -> createContact(user)).collect(toList());
   }

   protected Contact createContact(ComponentTestUser user) {
      var contact = contact(user);
      return contactRepository.save(contact);
   }
}
