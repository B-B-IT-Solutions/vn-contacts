package cz.prm.controllers.mappers;

import static java.util.Objects.isNull;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.Page;
import cz.prm.domain.common.Pagination;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {

   PageDto<ContactDto> toPageDto(Page<Contact> contacts);

   ContactDto toContactDto(Contact contact);

   Contact toContact(ContactDto dto);

   ContactsQuery toContactsQuery(ContactsQueryDto dto);

   ContactsFilter toContactsFilter(ContactsFilterDto dto);

   default ContactsQuery toNullSafeContactsQuery(ContactsQueryDto dto) {
      if (isNull(dto)) {
         return new ContactsQuery();
      }
      return toContactsQuery(dto);
   }

   @AfterMapping
   default void afterContactsQuery(ContactsQueryDto source, @MappingTarget ContactsQuery target) {
      if (isNull(target.getPagination())) {
         target.setPagination(new Pagination());
      }
      if (isNull(target.getFilter())) {
         target.setFilter(new ContactsFilter());
      }
   }

}
