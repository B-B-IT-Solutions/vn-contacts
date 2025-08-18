package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

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

public class SettingsUtils {

    public static AccountSettings accountSettings() {
        var settings = new AccountSettings();
        settings.setSettingsId(randomLong());
        settings.setAppLanguage(uuid());
        return settings;
    }

    public static ContactSettings contactSettings() {
        var settings = new ContactSettings();
        settings.setLabels(labels());
        settings.setIndustries(industries());
        settings.setSkills(skills());
        settings.setProducts(products());
        settings.setTargetMarkets(targetMarkets());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static ContactSettingsDto contactSettingsDto() {
        var settings = new ContactSettingsDto();
        settings.setLabels(labelsDto());
        settings.setIndustries(industriesDto());
        settings.setSkills(skillsDto());
        settings.setProducts(productsDto());
        settings.setTargetMarkets(targetMarketsDto());
        settings.setLastEditDate(now());
        return settings;
    }

    public static NoteSettings noteSettings() {
        var settings = new NoteSettings();
        settings.setCategories(categories());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static NoteSettingsDto noteSettingsDto() {
        var settings = new NoteSettingsDto();
        settings.setCategories(categoriesDto());
        settings.setLastEditDate(now());
        return settings;
    }

    public static NotificationSettings notificationSettings() {
        var settings = new NotificationSettings();
        settings.setSettingsId(randomLong());
        settings.setGlobal(ALL);
        settings.setContact(contactNotifications());
        settings.setReferral(referralNotifications());
        settings.setTask(taskNotifications());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static NotificationSettingsDto notificationSettingsDto() {
        var settings = new NotificationSettingsDto();
        settings.setSettingsId(randomLong());
        settings.setGlobal(ALL);
        settings.setContact(contactNotificationsDto());
        settings.setReferral(referralNotificationsDto());
        settings.setTask(taskNotificationsDto());
        settings.setLastEditDate(now());
        return settings;
    }

    public static List<Label> labels() {
        return newArrayList(label(), label(), label());
    }

    public static List<LabelDto> labelsDto() {
        return newArrayList(labelDto(), labelDto(), labelDto());
    }

    public static Label label() {
        var label = new Label();
        label.setValue(uuid());
        label.setColor(uuid());
        return label;
    }

    public static LabelDto labelDto() {
        var label = new LabelDto();
        label.setValue(uuid());
        label.setColor(uuid());
        return label;
    }

    public static List<Industry> industries() {
        return newArrayList(industry(), industry(), industry());
    }

    public static List<IndustryDto> industriesDto() {
        return newArrayList(industryDto(), industryDto(), industryDto());
    }

    public static Industry industry() {
        var industry = new Industry();
        industry.setValue(uuid());
        industry.setColor(uuid());
        return industry;
    }

    public static IndustryDto industryDto() {
        var industry = new IndustryDto();
        industry.setValue(uuid());
        industry.setColor(uuid());
        return industry;
    }

    public static List<Skill> skills() {
        return newArrayList(skill(), skill(), skill());
    }

    public static List<SkillDto> skillsDto() {
        return newArrayList(skillDto(), skillDto(), skillDto());
    }

    public static Skill skill() {
        var skill = new Skill();
        skill.setValue(uuid());
        skill.setColor(uuid());
        return skill;
    }

    public static SkillDto skillDto() {
        var skill = new SkillDto();
        skill.setValue(uuid());
        skill.setColor(uuid());
        return skill;
    }

    public static List<Product> products() {
        return newArrayList(product(), product(), product());
    }

    public static List<ProductDto> productsDto() {
        return newArrayList(productDto(), productDto(), productDto());
    }

    public static Product product() {
        var product = new Product();
        product.setValue(uuid());
        product.setColor(uuid());
        return product;
    }

    public static ProductDto productDto() {
        var product = new ProductDto();
        product.setValue(uuid());
        product.setColor(uuid());
        return product;
    }

    public static List<TargetMarket> targetMarkets() {
        return newArrayList(targetMarket(), targetMarket(), targetMarket());
    }

    public static List<TargetMarketDto> targetMarketsDto() {
        return newArrayList(targetMarketDto(), targetMarketDto(), targetMarketDto());
    }

    public static TargetMarket targetMarket() {
        var targetMarket = new TargetMarket();
        targetMarket.setValue(uuid());
        targetMarket.setColor(uuid());
        return targetMarket;
    }

    public static TargetMarketDto targetMarketDto() {
        var targetMarket = new TargetMarketDto();
        targetMarket.setValue(uuid());
        targetMarket.setColor(uuid());
        return targetMarket;
    }

    public static List<Category> categories() {
        return newArrayList(category(), category(), category());
    }

    public static List<CategoryDto> categoriesDto() {
        return newArrayList(categoryDto(), categoryDto(), categoryDto());
    }

    public static Category category() {
        var category = new Category();
        category.setValue(uuid());
        category.setColor(uuid());
        return category;
    }

    public static CategoryDto categoryDto() {
        var category = new CategoryDto();
        category.setValue(uuid());
        category.setColor(uuid());
        return category;
    }

    public static ContactNotifications contactNotifications() {
        var settings = new ContactNotifications();
        settings.setStalenessReminder(true);
        return settings;
    }

    public static ContactNotificationsDto contactNotificationsDto() {
        var dto = new ContactNotificationsDto();
        dto.setStalenessReminder(true);
        return dto;
    }

    public static ReferralNotifications referralNotifications() {
        var settings = new ReferralNotifications();
        settings.setFollowupReminder(true);
        settings.setExpiryReminder(true);
        settings.setStalenessReminder(true);
        return settings;
    }

    public static ReferralNotificationsDto referralNotificationsDto() {
        var dto = new ReferralNotificationsDto();
        dto.setFollowupReminder(true);
        dto.setExpiryReminder(true);
        dto.setStalenessReminder(true);
        return dto;
    }

    public static TaskNotifications taskNotifications() {
        var settings = new TaskNotifications();
        settings.setReminders(true);
        settings.setAboutToExpire(true);
        return settings;
    }

    public static TaskNotificationsDto taskNotificationsDto() {
        var dto = new TaskNotificationsDto();
        dto.setReminders(true);
        dto.setAboutToExpire(true);
        return dto;
    }
}
