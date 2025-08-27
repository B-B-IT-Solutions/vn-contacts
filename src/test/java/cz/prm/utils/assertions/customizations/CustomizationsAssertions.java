package cz.prm.utils.assertions.customizations;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Objects;

public class CustomizationsAssertions {

    public static void assertSettings(ContactCustomizations settings1, ContactCustomizations settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getLabels()).containsExactlyElementsOf(settings2.getLabels());
        assertThat(settings1.getIndustries()).containsExactlyElementsOf(settings2.getIndustries());
        assertThat(settings1.getSkills()).containsExactlyElementsOf(settings2.getSkills());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
    }

    public static void assertSettings(ContactCustomizations settings, ContactCustomizationsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertLabels(settings.getLabels(), dto.getLabels());
        assertIndustries(settings.getIndustries(), dto.getIndustries());
        assertSkills(settings.getSkills(), dto.getSkills());
        assertProducts(settings.getProducts(), dto.getProducts());
        assertTargetMarkets(settings.getTargetMarkets(), dto.getTargetMarkets());
    }

    public static void assertSettings(NoteCustomizations settings1, NoteCustomizations settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getCategories()).containsExactlyElementsOf(settings2.getCategories());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
    }

    public static void assertSettings(NoteCustomizations settings, NoteCustomizationsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertCategories(settings.getCategories(), dto.getCategories());
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
        assertThat(industry.getDescription()).isEqualTo(dto.getDescription());
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
        assertThat(skill.getDescription()).isEqualTo(dto.getDescription());
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
        assertThat(product.getDescription()).isEqualTo(dto.getDescription());
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
        assertThat(targetMarket.getDescription()).isEqualTo(dto.getDescription());
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
        assertThat(label.getDescription()).isEqualTo(dto.getDescription());
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
        assertThat(category.getDescription()).isEqualTo(dto.getDescription());
    }
}
