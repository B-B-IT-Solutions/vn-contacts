package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.settings.ContactSettingsDto;
import cz.prm.controllers.dto.settings.IndustryDto;
import cz.prm.controllers.dto.settings.LabelDto;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.domain.settings.GeneralSettings;
import cz.prm.domain.settings.Industry;
import cz.prm.domain.settings.Label;
import java.util.List;

public class SettingsUtils {

    public static GeneralSettings generalSettings() {
        var settings = new GeneralSettings();
        settings.setSettingsId(randomLong());
        settings.setIndustries(industries());
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
        industry.setName(uuid());
        industry.setSystemDefined(true);
        return industry;
    }

    public static IndustryDto industryDto() {
        var industry = new IndustryDto();
        industry.setName(uuid());
        industry.setSystemDefined(true);
        return industry;
    }
}
