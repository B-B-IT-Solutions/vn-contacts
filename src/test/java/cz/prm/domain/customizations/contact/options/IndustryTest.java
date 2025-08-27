package cz.prm.domain.customizations.contact.options;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IndustryTest {

    @Test
    void newInstance() {
        var value = uuid();
        var industry = new Industry(value);
        assertThat(industry.getValue()).isEqualTo(value);
        assertThat(industry.getDescription()).isNull();
    }
}