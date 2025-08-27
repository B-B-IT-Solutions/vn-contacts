package cz.prm.utils.data.customizations;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.contact.options.IndustryDto;
import cz.prm.controllers.dto.customizations.contact.options.LabelDto;
import cz.prm.controllers.dto.customizations.contact.options.ProductDto;
import cz.prm.controllers.dto.customizations.contact.options.SkillDto;
import cz.prm.controllers.dto.customizations.contact.options.TargetMarketDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.options.CategoryDto;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.contact.options.Industry;
import cz.prm.domain.customizations.contact.options.Label;
import cz.prm.domain.customizations.contact.options.Product;
import cz.prm.domain.customizations.contact.options.Skill;
import cz.prm.domain.customizations.contact.options.TargetMarket;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.domain.customizations.note.options.Category;
import java.util.List;

public class CustomizationsUtils {

    public static ContactCustomizations contactCustomizations() {
        var settings = new ContactCustomizations();
        settings.setLabels(labels());
        settings.setIndustries(industries());
        settings.setSkills(skills());
        settings.setProducts(products());
        settings.setTargetMarkets(targetMarkets());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static ContactCustomizationsDto contactCustomizationsDto() {
        var settings = new ContactCustomizationsDto();
        settings.setLabels(labelsDto());
        settings.setIndustries(industriesDto());
        settings.setSkills(skillsDto());
        settings.setProducts(productsDto());
        settings.setTargetMarkets(targetMarketsDto());
        settings.setLastEditDate(now());
        return settings;
    }

    public static NoteCustomizations noteCustomizations() {
        var settings = new NoteCustomizations();
        settings.setCategories(categories());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static NoteCustomizationsDto noteCustomizationsDto() {
        var settings = new NoteCustomizationsDto();
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
        label.setDescription(uuid());
        return label;
    }

    public static LabelDto labelDto() {
        var label = new LabelDto();
        label.setValue(uuid());
        label.setDescription(uuid());
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
        industry.setDescription(uuid());
        return industry;
    }

    public static IndustryDto industryDto() {
        var industry = new IndustryDto();
        industry.setValue(uuid());
        industry.setDescription(uuid());
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
        skill.setDescription(uuid());
        return skill;
    }

    public static SkillDto skillDto() {
        var skill = new SkillDto();
        skill.setValue(uuid());
        skill.setDescription(uuid());
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
        product.setDescription(uuid());
        return product;
    }

    public static ProductDto productDto() {
        var product = new ProductDto();
        product.setValue(uuid());
        product.setDescription(uuid());
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
        targetMarket.setDescription(uuid());
        return targetMarket;
    }

    public static TargetMarketDto targetMarketDto() {
        var targetMarket = new TargetMarketDto();
        targetMarket.setValue(uuid());
        targetMarket.setDescription(uuid());
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
        category.setDescription(uuid());
        return category;
    }

    public static CategoryDto categoryDto() {
        var category = new CategoryDto();
        category.setValue(uuid());
        category.setDescription(uuid());
        return category;
    }
}
