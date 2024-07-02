package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.domain.settings.UserSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    SettingsDto toSettingsDto(UserSettings settings);

    @Mapping(target = "owner", ignore = true)
    UserSettings toSettings(SettingsDto dto);
}
