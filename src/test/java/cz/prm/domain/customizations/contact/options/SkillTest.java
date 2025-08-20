package cz.prm.domain.customizations.contact.options;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SkillTest {

    @Test
    void newInstance() {
        var value = uuid();
        var skill = new Skill(value);
        assertThat(skill.getValue()).isEqualTo(value);
        assertThat(skill.getColor()).isNull();
    }
}