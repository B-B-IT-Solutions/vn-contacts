package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.domain.settings.Settings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    SettingsDto toSettingsDto(Settings settings);

    @Mapping(target = "owner", ignore = true)
    Settings toSettings(SettingsDto dto);
}
