package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.domain.settings.GeneralSettings;
import cz.prm.domain.settings.UserSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    GeneralSettingsDto toGeneralSettingsDto(GeneralSettings settings);

    UserSettingsDto toUserSettingsDto(UserSettings settings);

    @Mapping(target = "owner", ignore = true)
    UserSettings toUserSettings(UserSettingsDto dto);
}
