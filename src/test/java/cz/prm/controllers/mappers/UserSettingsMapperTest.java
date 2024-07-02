package cz.prm.controllers.mappers;

import static cz.prm.utils.SettingsUtils.userSettings;
import static cz.prm.utils.SettingsUtils.userSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class UserSettingsMapperTest {

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();

    @Test
    void toSettingsDto() {
        var settings = userSettings();
        var dto = mapper.toSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toSettings() {
        var dto = userSettingsDto();
        var settings = mapper.toSettings(dto);
        assertSettings(settings, dto);
    }
}