package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUtils.uuid;

import cz.prm.controllers.dto.settings.contact.IndustryDto;
import cz.prm.controllers.dto.settings.contact.LabelDto;
import cz.prm.controllers.dto.settings.note.CategoryDto;
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
        industry.setValue(uuid());
        industry.setColor(uuid());
        return industry;
    }

    public static List<CategoryDto> categoriesDto() {
        return newArrayList(categoryDto(), categoryDto(), categoryDto());
    }

    public static CategoryDto categoryDto() {
        var category = new CategoryDto();
        category.setValue(uuid());
        category.setColor(uuid());
        return category;
    }
}
