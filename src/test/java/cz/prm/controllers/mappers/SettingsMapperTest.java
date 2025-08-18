package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.accountSettings;
import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.contactSettingsDto;
import static cz.prm.utils.SettingsUtils.noteSettings;
import static cz.prm.utils.SettingsUtils.noteSettingsDto;
import static cz.prm.utils.SettingsUtils.notificationSettings;
import static cz.prm.utils.SettingsUtils.notificationSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class SettingsMapperTest {

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();

    @Test
    void toAccountSettingsDto() {
        var settings = accountSettings();
        var dto = mapper.toAccountSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactSettingsDto() {
        var settings = contactSettings();
        var dto = mapper.toContactSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactSettings() {
        var dto = contactSettingsDto();
        var settings = mapper.toContactSettings(dto);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteSettingsDto() {
        var settings = noteSettings();
        var dto = mapper.toNoteSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteSettings() {
        var dto = noteSettingsDto();
        var settings = mapper.toNoteSettings(dto);
        assertSettings(settings, dto);
    }

    @Test
    void toNotificationSettingsDto() {
        var settings = notificationSettings();
        var dto = mapper.toNotificationSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toNotificationSettings() {
        var dto = notificationSettingsDto();
        var settings = mapper.toNotificationSettings(dto);
        assertSettings(settings, dto);
    }
}