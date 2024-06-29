package cz.prm.business.settings;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.utils.assertions.SettingsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class SettingsComponentTest extends SettingsComponentTestBase {

    @Test
    void getSettings() {
        var settingsDto = user1GetSettings();
        assertSettings(settingsDto);

        settingsDto = user2GetSettings();
        assertSettings(settingsDto);

        settingsDto = user3GetSettings();
        assertSettings(settingsDto);
    }

    private void assertSettings(SettingsDto dto) {
        var settings = settingsRepository.getById(dto.getSettingsId());
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }
}
