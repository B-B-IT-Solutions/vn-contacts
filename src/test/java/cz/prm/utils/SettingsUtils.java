package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.settings.LabelDto;
import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.domain.settings.Label;
import cz.prm.domain.settings.UserSettings;
import java.util.List;

public class SettingsUtils {

    public static UserSettings settings() {
        var settings = new UserSettings();
        settings.setLabels(labels());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static SettingsDto settingsDto() {
        var settings = new SettingsDto();
        settings.setLabels(labelsDto());
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
}
