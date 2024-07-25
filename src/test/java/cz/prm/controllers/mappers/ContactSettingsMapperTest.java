package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.generalSettings;
import static cz.prm.utils.SettingsUtils.userSettings;
import static cz.prm.utils.SettingsUtils.userSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ContactSettingsMapperTest {

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();

    @Test
    void toGeneralSettingsDto() {
        var settings = generalSettings();
        var dto = mapper.toGeneralSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactSettingsDto() {
        var settings = userSettings();
        var dto = mapper.toContactSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactSettings() {
        var dto = userSettingsDto();
        var settings = mapper.toContactSettings(dto);
        assertSettings(settings, dto);
    }
}