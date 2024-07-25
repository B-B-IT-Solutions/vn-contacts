package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.ContactSettingsDto;
import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.IndustryDto;
import cz.prm.controllers.dto.settings.LabelDto;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.domain.settings.GeneralSettings;
import cz.prm.domain.settings.Industry;
import cz.prm.domain.settings.Label;
import java.util.List;
import java.util.Objects;

public class SettingsAssertions {

    public static void assertSettings(GeneralSettings settings1, GeneralSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getIndustries()).containsExactlyElementsOf(settings2.getIndustries());
    }

    public static void assertSettings(GeneralSettings settings, GeneralSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertIndustries(settings.getIndustries(), dto.getIndustries());
    }

    public static void assertSettings(ContactSettings settings1, ContactSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getLabels()).containsExactlyElementsOf(settings2.getLabels());
        assertThat(settings1.getIndustries()).containsExactlyElementsOf(settings2.getIndustries());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
    }

    public static void assertSettings(ContactSettings settings, ContactSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertLabels(settings.getLabels(), dto.getLabels());
        assertIndustries(settings.getIndustries(), dto.getIndustries());
    }

    public static void assertIndustries(List<Industry> industries, List<IndustryDto> dtos) {
        assertThat(industries).isNotEmpty().hasSameSizeAs(dtos);
        industries.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getName(), u.getName())).findFirst().get();
            assertIndustry(u1, u2);
        });
    }

    public static void assertIndustry(Industry industry, IndustryDto dto) {
        assertThat(industry.getName()).isEqualTo(dto.getName());
        assertThat(industry.isSystemDefined()).isEqualTo(dto.isSystemDefined());
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
