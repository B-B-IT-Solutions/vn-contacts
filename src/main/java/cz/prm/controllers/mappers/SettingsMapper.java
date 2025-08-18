package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    AccountSettingsDto toAccountSettingsDto(AccountSettings settings);

    ContactSettingsDto toContactSettingsDto(ContactSettings settings);

    @Mapping(target = "owner", ignore = true)
    ContactSettings toContactSettings(ContactSettingsDto dto);

    NoteSettingsDto toNoteSettingsDto(NoteSettings settings);

    @Mapping(target = "owner", ignore = true)
    NoteSettings toNoteSettings(NoteSettingsDto dto);

    NotificationSettingsDto toNotificationSettingsDto(NotificationSettings settings);

    @Mapping(target = "owner", ignore = true)
    NotificationSettings toNotificationSettings(NotificationSettingsDto dto);
}
