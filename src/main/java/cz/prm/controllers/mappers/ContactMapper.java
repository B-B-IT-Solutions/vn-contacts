package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

   List<ContactDto> toContactsDto(List<Contact> contacts);

   ContactDto toContactDto(Contact contact);

   Contact toContact(ContactDto dto);
}
