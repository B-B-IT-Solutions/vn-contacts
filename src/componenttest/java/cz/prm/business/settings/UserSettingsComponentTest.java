package cz.prm.business.settings;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.utils.assertions.SettingsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class UserSettingsComponentTest extends SettingsComponentTestBase {

    @Test
    void getSettings() {
        var dto_1 = user1GetSettings();
        var dto_2 = user1GetSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);

        dto_1 = user2GetSettings();
        dto_2 = user2GetSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);

        dto_1 = user3GetSettings();
        dto_2 = user3GetSettings();
        assertThat(dto_1).isEqualTo(dto_2);
        assertSettings(dto_2);
    }

    private void assertSettings(SettingsDto dto) {
        var settings = getSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }
}
