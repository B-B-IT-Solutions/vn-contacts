package cz.prm.controllers.mappers;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.UserAssertions.assertContact;
import static cz.prm.utils.assertions.UserAssertions.assertContactsDto;

import cz.prm.utils.MapperUtils;
import cz.prm.utils.assertions.UserAssertions;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

   private ContactMapper mapper = MapperUtils.getUserMapper();

   @Test
   void toUsersDto() {
      var users = contacts();
      var dtos = mapper.toUsersDto(users);
      assertContactsDto(users, dtos);
   }

   @Test
   void toUserDto() {
      var user = contact();
      var dto = mapper.toUserDto(user);
      UserAssertions.assertContact(user, dto);
   }
}