package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.contact.options.IndustryDto;
import cz.prm.controllers.dto.customizations.contact.options.LabelDto;
import cz.prm.controllers.dto.customizations.contact.options.ProductDto;
import cz.prm.controllers.dto.customizations.contact.options.SkillDto;
import cz.prm.controllers.dto.customizations.contact.options.TargetMarketDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.options.CategoryDto;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import java.util.List;
import java.util.Objects;

public class SettingsComponentTestAssertions {

    public static void assertSettings(AccountSettings settings, AccountSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getAppLanguage()).isEqualTo(dto.getAppLanguage());
    }

    public static void assertSettings(ContactCustomizations settings, ContactCustomizationsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getLabels()).isEmpty();
        assertThat(dto.getSkills()).isNotEmpty();
        assertThat(dto.getIndustries()).isNotEmpty();
        assertThat(dto.getProducts()).isNotEmpty();
        assertThat(dto.getTargetMarkets()).isNotEmpty();
    }

    public static void assertSettings(ContactCustomizationsDto dto1, ContactCustomizationsDto dto2) {
        assertThat(dto1.getSettingsId()).isEqualTo(dto2.getSettingsId());
        assertLabelsDto(dto1.getLabels(), dto2.getLabels());
        assertIndustriesDto(dto1.getIndustries(), dto2.getIndustries());
        assertSkillsDto(dto1.getSkills(), dto2.getSkills());
        assertProducts(dto1.getProducts(), dto2.getProducts());
        assertTargetMarkets(dto1.getTargetMarkets(), dto2.getTargetMarkets());
    }

    public static void assertSettings(NoteCustomizations settings, NoteCustomizationsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getCategories()).isEmpty();
    }

    public static void assertSettings(NoteCustomizationsDto dto1, NoteCustomizationsDto dto2) {
        assertThat(dto1.getSettingsId()).isEqualTo(dto2.getSettingsId());
        assertCategoriesDto(dto1.getCategories(), dto2.getCategories());
    }

    public static void assertSettings(NotificationSettings settings, NotificationSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getGlobal()).isNotNull();
        assertThat(dto.getContact()).isNotNull();
        assertThat(dto.getReferral()).isNotNull();
        assertThat(dto.getTask()).isNotNull();
    }

    public static void assertSettings(NotificationSettingsDto settings1, NotificationSettingsDto settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getGlobal()).isEqualTo(settings2.getGlobal());
        assertNotificationsDto(settings1.getContact(), settings2.getContact());
        assertNotificationsDto(settings1.getReferral(), settings2.getReferral());
        assertNotificationsDto(settings1.getTask(), settings2.getTask());
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

    public static void assertSkillsDto(List<SkillDto> dtos1, List<SkillDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertSkill(u1, u2);
        });
    }

    public static void assertSkill(SkillDto dto1, SkillDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }

    public static void assertProducts(List<ProductDto> dtos1, List<ProductDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertProduct(u1, u2);
        });
    }

    public static void assertProduct(ProductDto dto1, ProductDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }

    public static void assertTargetMarkets(List<TargetMarketDto> dtos1, List<TargetMarketDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertTargetMarket(u1, u2);
        });
    }

    public static void assertTargetMarket(TargetMarketDto dto1, TargetMarketDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }

    public static void assertCategoriesDto(List<CategoryDto> dtos1, List<CategoryDto> dtos2) {
        assertThat(dtos1).isNotEmpty().hasSameSizeAs(dtos2);
        dtos1.forEach(u1 -> {
            var u2 = dtos2.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertCategoryDto(u1, u2);
        });
    }

    public static void assertCategoryDto(CategoryDto dto1, CategoryDto dto2) {
        assertThat(dto1.getValue()).isEqualTo(dto2.getValue());
        assertThat(dto1.getColor()).isEqualTo(dto2.getColor());
    }

    public static void assertNotificationsDto(ContactNotificationsDto dto1, ContactNotificationsDto dto2) {
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotificationsDto(ReferralNotificationsDto dto1, ReferralNotificationsDto dto2) {
        assertThat(dto1.isFollowupReminder()).isEqualTo(dto2.isFollowupReminder());
        assertThat(dto1.isExpiryReminder()).isEqualTo(dto2.isExpiryReminder());
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotificationsDto(TaskNotificationsDto dto1, TaskNotificationsDto dto2) {
        assertThat(dto1.isReminders()).isEqualTo(dto2.isReminders());
        assertThat(dto1.isAboutToExpire()).isEqualTo(dto2.isAboutToExpire());
    }
}
