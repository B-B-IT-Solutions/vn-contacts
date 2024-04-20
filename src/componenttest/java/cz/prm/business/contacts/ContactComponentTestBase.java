package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ContactComponentTestUtils.contact;
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
import org.springframework.beans.factory.annotation.Autowired;

public class ContactComponentTestBase extends ComponentTestBase {

   private static String CONTACTS_URL = "contacts";
   @Autowired
   protected ContactRepository contactRepository;
   @Autowired
   protected ContactService contactService;

   protected List<ContactDto> user1GetContacts() {
      return getContacts(USER_1);
   }

   protected List<ContactDto> user2GetContacts() {
      return getContacts(USER_2);
   }

   protected List<ContactDto> user3GetContacts() {
      return getContacts(USER_3);
   }

   protected List<ContactDto> getContacts(ComponentTestUser user) {
      var typeRef = new TypeRef<List<ContactDto>>() {
      };
      return getMany(CONTACTS_URL, user, typeRef);
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
