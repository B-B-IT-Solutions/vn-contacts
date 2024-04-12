package cz.prm.controllers.mappers;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.UserAssertions.assertContactsDto;

import cz.prm.utils.MapperUtils;
import cz.prm.utils.assertions.UserAssertions;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

   private ContactMapper mapper = MapperUtils.getContactMapper();

   @Test
   void toContactsDto() {
      var users = contacts();
      var dtos = mapper.toContactsDto(users);
      assertContactsDto(users, dtos);
   }

   @Test
   void toContactDto() {
      var user = contact();
      var dto = mapper.toContactDto(user);
      UserAssertions.assertContact(user, dto);
   }
}