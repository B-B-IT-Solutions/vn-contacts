package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

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
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static ContactSettingsDto contactSettingsDto() {
        var settings = new ContactSettingsDto();
        settings.setLabels(labelsDto());
        settings.setIndustries(industriesDto());
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
}
