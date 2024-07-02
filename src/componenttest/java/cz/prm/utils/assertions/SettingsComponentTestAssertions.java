package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.domain.settings.GeneralSettings;
import cz.prm.domain.settings.UserSettings;

public class SettingsComponentTestAssertions {

    public static void assertSettings(GeneralSettings settings, GeneralSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getIndustries()).containsExactlyElementsOf(dto.getIndustries());
        assertThat(dto.getIndustries()).hasSize(116);
    }

    public static void assertSettings(UserSettings settings, UserSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getLabels()).isEmpty();
    }
}
