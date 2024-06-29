package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.LabelDto;
import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.domain.settings.Label;
import cz.prm.domain.settings.Settings;
import java.util.List;
import java.util.Objects;

public class SettingsComponentTestAssertions {

    public static void assertSettings(Settings settings, SettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertLabels(settings.getLabels(), dto.getLabels());
    }

    public static void assertLabels(List<Label> labels, List<LabelDto> dtos) {
        assertThat(labels).isNotEmpty().hasSameSizeAs(dtos);
        labels.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertLabel(u1, u2);
        });
    }

    public static void assertLabel(Label label, LabelDto dto) {
        assertThat(label.getValue()).isEqualTo(dto.getValue());
        assertThat(label.getColor()).isEqualTo(dto.getColor());
    }
}
