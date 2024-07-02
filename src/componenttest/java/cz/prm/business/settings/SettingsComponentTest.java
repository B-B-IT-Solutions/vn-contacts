package cz.prm.business.settings;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.utils.assertions.SettingsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class SettingsComponentTest extends SettingsComponentTestBase {

    @Test
    void getGeneralSettings() {
        var dto = user1GetGeneralSettings();
        assertSettings(dto);

        dto = user2GetGeneralSettings();
        assertSettings(dto);

        dto = user3GetGeneralSettings();
        assertSettings(dto);
    }

    @Test
    void getUserSettings() {
        var dto_1 = user1GetUserSettings();
        var dto_2 = user1GetUserSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);

        dto_1 = user2GetUserSettings();
        dto_2 = user2GetUserSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);

        dto_1 = user3GetUserSettings();
        dto_2 = user3GetUserSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);
    }

    private void assertSettings(GeneralSettingsDto dto) {
        var settings = getGeneralSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(UserSettingsDto dto) {
        var settings = getUserSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }
}
