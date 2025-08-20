package cz.prm.domain.customizations.contact;

import static com.google.common.collect.Lists.newArrayList;
import static lombok.AccessLevel.PRIVATE;

import cz.prm.domain.customizations.contact.options.Industry;
import cz.prm.domain.customizations.contact.options.Product;
import cz.prm.domain.customizations.contact.options.Skill;
import cz.prm.domain.customizations.contact.options.TargetMarket;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class InitContactCustomizations {

    public static final List<Industry> INITIAL_INDUSTRIES = newArrayList(new Industry("Construction"), new Industry("Data Science"),
        new Industry("Education"), new Industry("Finance"), new Industry("Healthcare"), new Industry("Health & Fitness"),
        new Industry("Human Resources"), new Industry("Information Technology"), new Industry("Retail"), new Industry("Real estate"));

    public static final List<Skill> INITIAL_SKILLS = newArrayList(new Skill("Marketing Strategy"), new Skill("Web Development"), new Skill("SEO"),
        new Skill("Graphic Design"), new Skill("Sales Automation"), new Skill("Financial Planning"), new Skill("Data Analysis"));

    public static final List<Product> INITIAL_PRODUCTS = newArrayList(new Product("Consulting"), new Product("Web Design"),
        new Product("Custom Software"), new Product("Online Courses"), new Product("SaaS Product"), new Product("Managed Services"),
        new Product("Training & Workshops"));

    public static final List<TargetMarket> INITIAL_TARGET_MARKETS = newArrayList(new TargetMarket("North America"), new TargetMarket("Europe"),
        new TargetMarket("Asia-Pacific"), new TargetMarket("Small Businesses"), new TargetMarket("Enterprise Clients"), new TargetMarket("Startups"),
        new TargetMarket("Healthcare"), new TargetMarket("Retail"), new TargetMarket("B2B"), new TargetMarket("B2C"), new TargetMarket("Government"),
        new TargetMarket("Tech Companies"), new TargetMarket("Education"));
}
