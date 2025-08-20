package cz.prm.controllers.mapper.customizations;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomizationsMapper {

    ContactCustomizationsDto toContactSettingsDto(ContactCustomizations settings);

    @Mapping(target = "owner", ignore = true)
    ContactCustomizations toContactSettings(ContactCustomizationsDto dto);

    NoteCustomizationsDto toNoteSettingsDto(NoteCustomizations settings);

    @Mapping(target = "owner", ignore = true)
    NoteCustomizations toNoteSettings(NoteCustomizationsDto dto);
}
