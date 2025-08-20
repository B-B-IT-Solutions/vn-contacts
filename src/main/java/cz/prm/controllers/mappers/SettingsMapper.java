package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    AccountSettingsDto toAccountSettingsDto(AccountSettings settings);

    NotificationSettingsDto toNotificationSettingsDto(NotificationSettings settings);

    @Mapping(target = "owner", ignore = true)
    NotificationSettings toNotificationSettings(NotificationSettingsDto dto);
}
