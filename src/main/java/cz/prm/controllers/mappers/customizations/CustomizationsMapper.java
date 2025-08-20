package cz.prm.controllers.mappers.customizations;

import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.domain.customizations.contact.ContactSettings;
import cz.prm.domain.customizations.note.NoteSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomizationsMapper {

    ContactSettingsDto toContactSettingsDto(ContactSettings settings);

    @Mapping(target = "owner", ignore = true)
    ContactSettings toContactSettings(ContactSettingsDto dto);

    NoteSettingsDto toNoteSettingsDto(NoteSettings settings);

    @Mapping(target = "owner", ignore = true)
    NoteSettings toNoteSettings(NoteSettingsDto dto);
}
