package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.contact.IndustryDto;
import cz.prm.controllers.dto.settings.contact.LabelDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import java.util.List;
import java.util.Objects;

public class SettingsComponentTestAssertions {

    public static void assertSettings(AccountSettings settings, AccountSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getAppLanguage()).isEqualTo(dto.getAppLanguage());
    }

    public static void assertSettings(ContactSettings settings, ContactSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getLabels()).isEmpty();
    }

    public static void assertSettings(ContactSettingsDto dto1, ContactSettingsDto dto2) {
        assertThat(dto1.getSettingsId()).isEqualTo(dto2.getSettingsId());
        assertLabelsDto(dto1.getLabels(), dto2.getLabels());
        assertIndustriesDto(dto1.getIndustries(), dto2.getIndustries());
    }

    public static void assertLabelsDto(List<LabelDto> dtos1, List<LabelDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertLabelDto(u1, u2);
        });
    }

    public static void assertLabelDto(LabelDto dto1, LabelDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }

    public static void assertIndustriesDto(List<IndustryDto> dtos1, List<IndustryDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertIndustryDto(u1, u2);
        });
    }

    public static void assertIndustryDto(IndustryDto dto1, IndustryDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }
}
