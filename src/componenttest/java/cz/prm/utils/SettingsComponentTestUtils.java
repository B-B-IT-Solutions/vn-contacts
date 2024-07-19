package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.uuid;

import com.google.common.collect.Lists;
import cz.prm.controllers.dto.settings.LabelDto;
import java.util.List;

public class SettingsComponentTestUtils {

    public static List<LabelDto> labelsDto() {
        return Lists.newArrayList(labelDto(), labelDto(), labelDto());
    }

    public static LabelDto labelDto() {
        var label = new LabelDto();
        label.setValue(uuid());
        label.setColor(uuid());
        return label;
    }
}
