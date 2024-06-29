package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.settings;
import static cz.prm.utils.SettingsUtils.settingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class SettingsMapperTest {

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();

    @Test
    void toSettingsDto() {
        var settings = settings();
        var dto = mapper.toSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toSettings() {
        var dto = settingsDto();
        var settings = mapper.toSettings(dto);
        assertSettings(settings, dto);
    }
}