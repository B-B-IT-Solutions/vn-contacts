package cz.prm.domain.settings.contact;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_INDUSTRIES;
import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_SKILLS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class InitContactSettingsTest {

    private static final List<Industry> EXPECTED_INITIAL_INDUSTRIES = newArrayList(new Industry("Construction"), new Industry("Data Science"),
        new Industry("Education"), new Industry("Finance"), new Industry("Healthcare"), new Industry("Health & Fitness"),
        new Industry("Human Resources"), new Industry("Information Technology"), new Industry("Retail"), new Industry("Real estate"));

    public static final List<Skill> EXPECTED_INITIAL_SKILLS = newArrayList(new Skill("Marketing Strategy"), new Skill("Web Development"),
        new Skill("SEO"), new Skill("Graphic Design"), new Skill("Sales Automation"), new Skill("Financial Planning"), new Skill("Data Analysis"));

    @Test
    void initialIndustries() {
        assertThat(INITIAL_INDUSTRIES).containsExactlyElementsOf(EXPECTED_INITIAL_INDUSTRIES);
    }

    @Test
    void initialSkills() {
        assertThat(INITIAL_SKILLS).containsExactlyElementsOf(EXPECTED_INITIAL_SKILLS);
    }
}