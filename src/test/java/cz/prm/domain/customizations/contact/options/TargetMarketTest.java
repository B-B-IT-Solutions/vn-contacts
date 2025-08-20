package cz.prm.domain.customizations.contact.options;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TargetMarketTest {

    @Test
    void newInstance() {
        var value = uuid();
        var targetMarket = new TargetMarket(value);
        assertThat(targetMarket.getValue()).isEqualTo(value);
        assertThat(targetMarket.getColor()).isNull();
    }
}