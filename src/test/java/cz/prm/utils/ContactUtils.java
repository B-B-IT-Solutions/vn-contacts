package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import org.assertj.core.util.Lists;

public class ContactUtils {

   public static List<Contact> contacts() {
      return Lists.newArrayList(contact(), contact(), contact());
   }

   public static Contact contact() {
      var user = new Contact();
      user.setContactId(randomLong());
      user.setFirstName(uuid());
      user.setLastName(uuid());
      user.setEmail(uuid());
      user.setOwner(uuid());
      return user;
   }

   public static ContactDto contactDto() {
      var user = new ContactDto();
      user.setContactId(randomLong());
      user.setFirstName(uuid());
      user.setLastName(uuid());
      user.setEmail(uuid());
      return user;
   }

}
