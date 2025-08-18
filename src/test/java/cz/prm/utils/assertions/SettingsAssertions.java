package cz.prm.utils.assertions;

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
import cz.prm.domain.settings.contact.Industry;
import cz.prm.domain.settings.contact.Label;
import cz.prm.domain.settings.contact.Product;
import cz.prm.domain.settings.contact.Skill;
import cz.prm.domain.settings.contact.TargetMarket;
import cz.prm.domain.settings.note.Category;
import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
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
        assertThat(settings1.getSkills()).containsExactlyElementsOf(settings2.getSkills());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
    }

    public static void assertSettings(ContactSettings settings, ContactSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertLabels(settings.getLabels(), dto.getLabels());
        assertIndustries(settings.getIndustries(), dto.getIndustries());
        assertSkills(settings.getSkills(), dto.getSkills());
        assertProducts(settings.getProducts(), dto.getProducts());
        assertTargetMarkets(settings.getTargetMarkets(), dto.getTargetMarkets());
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

    public static void assertSettings(NotificationSettings settings1, NotificationSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getGlobal()).isEqualTo(settings2.getGlobal());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
        assertNotifications(settings1.getContact(), settings2.getContact());
        assertNotifications(settings1.getReferral(), settings2.getReferral());
        assertNotifications(settings1.getTask(), settings2.getTask());
    }

    public static void assertSettings(NotificationSettings settings, NotificationSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getGlobal()).isEqualTo(dto.getGlobal());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertNotifications(settings.getContact(), dto.getContact());
        assertNotifications(settings.getReferral(), dto.getReferral());
        assertNotifications(settings.getTask(), dto.getTask());
    }

    public static void assertNotifications(ContactNotifications settings1, ContactNotifications settings2) {
        assertThat(settings1.isStalenessReminder()).isEqualTo(settings2.isStalenessReminder());
    }

    public static void assertNotifications(ContactNotifications settings, ContactNotificationsDto dto) {
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotifications settings1, ReferralNotifications settings2) {
        assertThat(settings1.isFollowupReminder()).isEqualTo(settings2.isFollowupReminder());
        assertThat(settings1.isExpiryReminder()).isEqualTo(settings2.isExpiryReminder());
        assertThat(settings1.isStalenessReminder()).isEqualTo(settings2.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotifications settings, ReferralNotificationsDto dto) {
        assertThat(settings.isFollowupReminder()).isEqualTo(dto.isFollowupReminder());
        assertThat(settings.isExpiryReminder()).isEqualTo(dto.isExpiryReminder());
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(TaskNotifications settings1, TaskNotifications settings2) {
        assertThat(settings1.isReminders()).isEqualTo(settings2.isReminders());
        assertThat(settings1.isAboutToExpire()).isEqualTo(settings2.isAboutToExpire());
    }

    public static void assertNotifications(TaskNotifications settings, TaskNotificationsDto dto) {
        assertThat(settings.isReminders()).isEqualTo(dto.isReminders());
        assertThat(settings.isAboutToExpire()).isEqualTo(dto.isAboutToExpire());
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

    public static void assertSkills(List<Skill> skills, List<SkillDto> dtos) {
        assertThat(skills).isNotEmpty().hasSameSizeAs(dtos);
        skills.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertSkill(u1, u2);
        });
    }

    public static void assertSkill(Skill skill, SkillDto dto) {
        assertThat(skill.getValue()).isEqualTo(dto.getValue());
        assertThat(skill.getColor()).isEqualTo(dto.getColor());
    }

    public static void assertProducts(List<Product> products, List<ProductDto> dtos) {
        assertThat(products).isNotEmpty().hasSameSizeAs(dtos);
        products.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertProduct(u1, u2);
        });
    }

    public static void assertProduct(Product product, ProductDto dto) {
        assertThat(product.getValue()).isEqualTo(dto.getValue());
        assertThat(product.getColor()).isEqualTo(dto.getColor());
    }

    public static void assertTargetMarkets(List<TargetMarket> targetMarkets, List<TargetMarketDto> dtos) {
        assertThat(targetMarkets).isNotEmpty().hasSameSizeAs(dtos);
        targetMarkets.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getValue(), u.getValue())).findFirst().get();
            assertTargetMarket(u1, u2);
        });
    }

    public static void assertTargetMarket(TargetMarket targetMarket, TargetMarketDto dto) {
        assertThat(targetMarket.getValue()).isEqualTo(dto.getValue());
        assertThat(targetMarket.getColor()).isEqualTo(dto.getColor());
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
