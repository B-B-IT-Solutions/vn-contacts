package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUtils.uuid;

import cz.prm.controllers.dto.customizations.contact.options.IndustryDto;
import cz.prm.controllers.dto.customizations.contact.options.LabelDto;
import cz.prm.controllers.dto.customizations.contact.options.ProductDto;
import cz.prm.controllers.dto.customizations.contact.options.SkillDto;
import cz.prm.controllers.dto.customizations.contact.options.TargetMarketDto;
import cz.prm.controllers.dto.customizations.note.options.CategoryDto;
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

    public static List<SkillDto> skillsDto() {
        return newArrayList(skillDto(), skillDto(), skillDto());
    }

    public static SkillDto skillDto() {
        var skill = new SkillDto();
        skill.setValue(uuid());
        skill.setColor(uuid());
        return skill;
    }

    public static List<ProductDto> productsDto() {
        return newArrayList(productDto(), productDto(), productDto());
    }

    public static ProductDto productDto() {
        var product = new ProductDto();
        product.setValue(uuid());
        product.setColor(uuid());
        return product;
    }

    public static List<TargetMarketDto> targetMarketsDto() {
        return newArrayList(targetMarketDto(), targetMarketDto(), targetMarketDto());
    }

    public static TargetMarketDto targetMarketDto() {
        var targetMarket = new TargetMarketDto();
        targetMarket.setValue(uuid());
        targetMarket.setColor(uuid());
        return targetMarket;
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
