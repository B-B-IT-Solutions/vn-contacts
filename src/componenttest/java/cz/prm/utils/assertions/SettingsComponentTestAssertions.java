package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.domain.settings.Settings;

public class SettingsComponentTestAssertions {

    public static void assertSettings(Settings settings, SettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getLabels()).isEmpty();
    }
}
