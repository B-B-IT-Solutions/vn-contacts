package cz.prm.controllers.mapper.customizations;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomizationsMapper {

    ContactCustomizationsDto toContactCustomizationsDto(ContactCustomizations settings);

    @Mapping(target = "owner", ignore = true)
    ContactCustomizations toContactCustomizations(ContactCustomizationsDto dto);

    NoteCustomizationsDto toNoteCustomizationsDto(NoteCustomizations settings);

    @Mapping(target = "owner", ignore = true)
    NoteCustomizations toNoteCustomizations(NoteCustomizationsDto dto);
}
