package cz.prm.business.settings;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.utils.assertions.SettingsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class UserSettingsComponentTest extends SettingsComponentTestBase {

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

    private void assertSettings(UserSettingsDto dto) {
        var settings = getSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }
}
