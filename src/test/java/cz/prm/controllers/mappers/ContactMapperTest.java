package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contactDto;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsFilterDto;
import static cz.prm.utils.ContactUtils.contactsQueryDto;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactFilter;
import static cz.prm.utils.assertions.ContactAssertions.assertContactQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertContacts;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

   private ContactMapper mapper = MapperUtils.getContactMapper();

   @Test
   void toPageDto() {
      var page = page(contacts());
      var dtos = mapper.toPageDto(page);
      assertContacts(page, dtos);
   }

   @Test
   void toContactDto() {
      var contact = contact();
      var dto = mapper.toContactDto(contact);
      assertContact(contact, dto);
   }

   @Test
   void toContact() {
      var dto = contactDto();
      var contact = mapper.toContact(dto);
      assertContact(contact, dto);
   }

   @Test
   void toQuery() {
      var dto = contactsQueryDto();
      var query = mapper.toQuery(dto);
      assertContactQuery(query, dto);
   }

   @Test
   void toFilter() {
      var dto = contactsFilterDto();
      var filter = mapper.toFilter(dto);
      assertContactFilter(filter, dto);
   }
}