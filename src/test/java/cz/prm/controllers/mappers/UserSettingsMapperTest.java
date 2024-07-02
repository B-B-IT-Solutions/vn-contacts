package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.generalSettings;
import static cz.prm.utils.SettingsUtils.userSettings;
import static cz.prm.utils.SettingsUtils.userSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class UserSettingsMapperTest {

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();

    @Test
    void toGeneralSettingsDto() {
        var settings = generalSettings();
        var dto = mapper.toGeneralSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toUserSettingsDto() {
        var settings = userSettings();
        var dto = mapper.toUserSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toUserSettings() {
        var dto = userSettingsDto();
        var settings = mapper.toUserSettings(dto);
        assertSettings(settings, dto);
    }
}