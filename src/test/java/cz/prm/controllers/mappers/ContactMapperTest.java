package cz.prm.controllers.mappers;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactsDto;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

   private ContactMapper mapper = MapperUtils.getContactMapper();

   @Test
   void toContactsDto() {
      var contacts = contacts();
      var dtos = mapper.toContactsDto(contacts);
      assertContactsDto(contacts, dtos);
   }

   @Test
   void toContactDto() {
      var contact = contact();
      var dto = mapper.toContactDto(contact);
      assertContact(contact, dto);
   }
}