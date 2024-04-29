package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.Page;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

   PageDto<ContactDto> toPageDto(Page<Contact> contacts);

   ContactDto toContactDto(Contact contact);

   Contact toContact(ContactDto dto);

   ContactsQuery toQuery(ContactsQueryDto queryDto);

   ContactsFilter toFilter(ContactsFilterDto queryDto);

}
