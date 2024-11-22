package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.contact.IndustryDto;
import cz.prm.controllers.dto.settings.contact.LabelDto;
import cz.prm.controllers.dto.settings.note.CategoryDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.domain.settings.contact.Industry;
import cz.prm.domain.settings.contact.Label;
import cz.prm.domain.settings.note.Category;
import cz.prm.domain.settings.note.NoteSettings;
import java.util.List;
import java.util.Objects;

public class SettingsAssertions {

    public static void assertSettings(AccountSettings settings1, AccountSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getAppLanguage()).isEqualTo(settings2.getAppLanguage());
    }

    public static void assertSettings(AccountSettings settings, AccountSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getAppLanguage()).isEqualTo(dto.getAppLanguage());
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

    public static void assertSettings(NoteSettings settings1, NoteSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getCategories()).containsExactlyElementsOf(settings2.getCategories());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
    }

    public static void assertSettings(NoteSettings settings, NoteSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertCategories(settings.getCategories(), dto.getCategories());
    }

    public static void assertIndustries(List<Industry> industries, List<IndustryDto> dtos) {
        assertThat(industries).isNotEmpty().hasSameSizeAs(dtos);
        industries.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertIndustry(u1, u2);
        });
    }

    public static void assertIndustry(Industry industry, IndustryDto dto) {
        assertThat(industry.getValue()).isEqualTo(dto.getValue());
        assertThat(industry.getColor()).isEqualTo(dto.getColor());
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

    public static void assertCategories(List<Category> categories, List<CategoryDto> dtos) {
        assertThat(categories).isNotEmpty().hasSameSizeAs(dtos);
        categories.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertCategory(u1, u2);
        });
    }

    public static void assertCategory(Category category, CategoryDto dto) {
        assertThat(category.getValue()).isEqualTo(dto.getValue());
        assertThat(category.getColor()).isEqualTo(dto.getColor());
    }
}
