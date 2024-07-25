package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.ContactSettingsDto;
import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.domain.settings.GeneralSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    GeneralSettingsDto toGeneralSettingsDto(GeneralSettings settings);

    ContactSettingsDto toContactSettingsDto(ContactSettings settings);

    @Mapping(target = "owner", ignore = true)
    ContactSettings toContactSettings(ContactSettingsDto dto);
}
