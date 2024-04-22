package cz.prm.utils;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.TestUtils.uuid;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import org.assertj.core.util.Lists;

public class ContactComponentTestUtils {

   public static List<Contact> contacts() {
      return contacts(USER_1);
   }

   public static List<Contact> contacts(ComponentTestUser user) {
      return Lists.newArrayList(contact(user), contact(user), contact(user));
   }

   public static Contact contact(ComponentTestUser user) {
      var contact = new Contact();
      contact.setFirstName(uuid());
      contact.setLastName(uuid());
      contact.setEmail(uuid());
      contact.setOwner(user.getUsername());
      return contact;
   }

   public static ContactDto contactDto() {
      var dto = new ContactDto();
      dto.setFirstName(uuid());
      dto.setLastName(uuid());
      dto.setEmail(uuid());
      return dto;
   }
}
