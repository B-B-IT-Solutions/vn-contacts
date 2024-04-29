package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.common.Page;
import cz.prm.domain.contact.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

   PageDto<ContactDto> toPageDto(Page<Contact> contacts);

   ContactDto toContactDto(Contact contact);

   Contact toContact(ContactDto dto);
}
