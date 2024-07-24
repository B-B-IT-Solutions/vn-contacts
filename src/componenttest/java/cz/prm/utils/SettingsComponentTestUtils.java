package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUtils.uuid;

import cz.prm.controllers.dto.settings.IndustryDto;
import cz.prm.controllers.dto.settings.LabelDto;
import java.util.List;

public class SettingsComponentTestUtils {

    public static List<LabelDto> labelsDto() {
        return newArrayList(labelDto(), labelDto(), labelDto());
    }

    public static LabelDto labelDto() {
        var label = new LabelDto();
        label.setValue(uuid());
        label.setColor(uuid());
        return label;
    }

    public static List<IndustryDto> industriesDto() {
        return newArrayList(industryDto(), industryDto(), industryDto());
    }

    public static IndustryDto industryDto() {
        var industry = new IndustryDto();
        industry.setName(TestUtils.uuid());
        industry.setSystemDefined(true);
        return industry;
    }
}
