package cz.prm.domain.customizations.contact.options;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_INDUSTRIES;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_PRODUCTS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_SKILLS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_TARGET_MARKETS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class InitContactCustomizationsTest {

    private static final List<Industry> EXPECTED_INITIAL_INDUSTRIES = newArrayList(new Industry("Construction"), new Industry("Data Science"),
        new Industry("Education"), new Industry("Finance"), new Industry("Healthcare"), new Industry("Health & Fitness"),
        new Industry("Human Resources"), new Industry("Information Technology"), new Industry("Retail"), new Industry("Real estate"));

    public static final List<Skill> EXPECTED_INITIAL_SKILLS = newArrayList(new Skill("Marketing Strategy"), new Skill("Web Development"),
        new Skill("SEO"), new Skill("Graphic Design"), new Skill("Sales Automation"), new Skill("Financial Planning"), new Skill("Data Analysis"));

    public static final List<Product> EXPECTED_INITIAL_PRODUCTS = newArrayList(new Product("Consulting"), new Product("Web Design"),
        new Product("Custom Software"), new Product("Online Courses"), new Product("SaaS Product"), new Product("Managed Services"),
        new Product("Training & Workshops"));

    public static final List<TargetMarket> EXPECTED_INITIAL_TARGET_MARKETS = newArrayList(new TargetMarket("North America"),
        new TargetMarket("Europe"), new TargetMarket("Asia-Pacific"), new TargetMarket("Small Businesses"), new TargetMarket("Enterprise Clients"),
        new TargetMarket("Startups"), new TargetMarket("Healthcare"), new TargetMarket("Retail"), new TargetMarket("B2B"), new TargetMarket("B2C"),
        new TargetMarket("Government"), new TargetMarket("Tech Companies"), new TargetMarket("Education"));

    @Test
    void initialIndustries() {
        assertThat(INITIAL_INDUSTRIES).containsExactlyElementsOf(EXPECTED_INITIAL_INDUSTRIES);
    }

    @Test
    void initialSkills() {
        assertThat(INITIAL_SKILLS).containsExactlyElementsOf(EXPECTED_INITIAL_SKILLS);
    }

    @Test
    void initialProducts() {
        assertThat(INITIAL_PRODUCTS).containsExactlyElementsOf(EXPECTED_INITIAL_PRODUCTS);
    }

    @Test
    void initialTargetMarkets() {
        assertThat(INITIAL_TARGET_MARKETS).containsExactlyElementsOf(EXPECTED_INITIAL_TARGET_MARKETS);
    }
}