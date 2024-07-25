package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.contactSettingsDto;
import static cz.prm.utils.SettingsUtils.generalSettings;
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
}