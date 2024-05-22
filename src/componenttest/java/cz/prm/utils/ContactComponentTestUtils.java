package cz.prm.utils;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.Contact;
import java.util.List;

public class ContactComponentTestUtils {

   public static List<Contact> contacts() {
      return contacts(USER_1);
   }

   public static List<Contact> contacts(ComponentTestUser user) {
      return newArrayList(contact(user), contact(user), contact(user));
   }

   public static Contact contact(ComponentTestUser user) {
      var contact = new Contact();
      contact.setFirstName(format("First%s", uuid()));
      contact.setMiddleName(format("Middle%s", uuid()));
      contact.setLastName(format("Last%s", uuid()));
      contact.setNickName(format("Nick%s", uuid()));
      contact.setEmail(format("email%s", uuid()));
      contact.setOwner(user.getUsername());
      return contact;
   }

   public static ContactDto contactDto() {
      var dto = new ContactDto();
      dto.setFirstName(uuid());
      dto.setMiddleName(uuid());
      dto.setLastName(uuid());
      dto.setNickName(uuid());
      dto.setEmail(uuid());
      return dto;
   }

   public static ContactsQueryDto contactsQueryDto() {
      var query = new ContactsQueryDto();
      query.setFilter(new ContactsFilterDto());
      query.setPagination(new PaginationDto());
      return query;
   }
}
