package cz.prm.utils.assertions;

import static cz.prm.utils.TimeComponentTestUtils.ONE_SECOND_OFFSET;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.contact.IndustryDto;
import cz.prm.controllers.dto.settings.contact.LabelDto;
import cz.prm.controllers.dto.settings.contact.ProductDto;
import cz.prm.controllers.dto.settings.contact.SkillDto;
import cz.prm.controllers.dto.settings.contact.TargetMarketDto;
import cz.prm.controllers.dto.settings.note.CategoryDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
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
        assertSkillsDto(dto1.getSkills(), dto2.getSkills());
        assertProducts(dto1.getProducts(), dto2.getProducts());
        assertTargetMarkets(dto1.getTargetMarkets(), dto2.getTargetMarkets());
    }

    public static void assertSettings(NoteSettings settings, NoteSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getCategories()).isEmpty();
    }

    public static void assertSettings(NoteSettingsDto dto1, NoteSettingsDto dto2) {
        assertThat(dto1.getSettingsId()).isEqualTo(dto2.getSettingsId());
        assertCategoriesDto(dto1.getCategories(), dto2.getCategories());
    }

    public static void assertSettings(NotificationSettingsDto settings1, NotificationSettingsDto settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getGlobal()).isEqualTo(settings2.getGlobal());
        assertThat(settings1.getLastEditDate()).isCloseTo(settings2.getLastEditDate(), ONE_SECOND_OFFSET);
        assertNotifications(settings1.getContact(), settings2.getContact());
        assertNotifications(settings1.getReferral(), settings2.getReferral());
        assertNotifications(settings1.getTask(), settings2.getTask());
    }

    public static void assertSettings(NotificationSettings settings, NotificationSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getGlobal()).isEqualTo(dto.getGlobal());
        assertThat(settings.getLastEditDate()).isCloseTo(dto.getLastEditDate(), ONE_SECOND_OFFSET);
        assertNotifications(settings.getContact(), dto.getContact());
        assertNotifications(settings.getReferral(), dto.getReferral());
        assertNotifications(settings.getTask(), dto.getTask());
    }

    public static void assertNotifications(ContactNotificationsDto dto1, ContactNotificationsDto dto2) {
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotifications(ContactNotifications settings, ContactNotificationsDto dto) {
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotificationsDto dto1, ReferralNotificationsDto dto2) {
        assertThat(dto1.isFollowupReminder()).isEqualTo(dto2.isFollowupReminder());
        assertThat(dto1.isExpiryReminder()).isEqualTo(dto2.isExpiryReminder());
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotifications settings, ReferralNotificationsDto dto) {
        assertThat(settings.isFollowupReminder()).isEqualTo(dto.isFollowupReminder());
        assertThat(settings.isExpiryReminder()).isEqualTo(dto.isExpiryReminder());
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(TaskNotificationsDto dto1, TaskNotificationsDto dto2) {
        assertThat(dto1.isReminders()).isEqualTo(dto2.isReminders());
        assertThat(dto1.isAboutToExpire()).isEqualTo(dto2.isAboutToExpire());
    }

    public static void assertNotifications(TaskNotifications settings, TaskNotificationsDto dto) {
        assertThat(settings.isReminders()).isEqualTo(dto.isReminders());
        assertThat(settings.isAboutToExpire()).isEqualTo(dto.isAboutToExpire());
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
}
