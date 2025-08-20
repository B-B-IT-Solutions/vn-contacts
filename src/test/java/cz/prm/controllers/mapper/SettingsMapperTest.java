package cz.prm.controllers.mapper;

import static cz.prm.utils.SettingsUtils.accountSettings;
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