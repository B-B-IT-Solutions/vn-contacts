package cz.prm.business.settings;

import static cz.prm.utils.SettingsComponentTestUtils.labelsDto;
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
        var dto1 = user1GetUserSettings();
        var dto2 = user1GetUserSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetUserSettings();
        dto2 = user2GetUserSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetUserSettings();
        dto2 = user3GetUserSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void updateUserSettings() {
        var dto1 = user1GetUserSettings();
        dto1.setLabels(labelsDto());
        user1UpdateUserSettings(dto1);
        var dto2 = user1GetUserSettings();
        assertSettings(dto1, dto2);

        dto1 = user2GetUserSettings();
        dto1.setLabels(labelsDto());
        user2UpdateUserSettings(dto1);
        dto2 = user2GetUserSettings();
        assertSettings(dto1, dto2);

        dto1 = user3GetUserSettings();
        dto1.setLabels(labelsDto());
        user3UpdateUserSettings(dto1);
        dto2 = user3GetUserSettings();
        assertSettings(dto1, dto2);
    }

    private void assertSettings(GeneralSettingsDto dto) {
        var settings = getGeneralSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(UserSettingsDto dto) {
        var settings = getUserSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(UserSettingsDto dto1, UserSettingsDto dto2) {
        SettingsComponentTestAssertions.assertSettings(dto1, dto2);
    }
}
