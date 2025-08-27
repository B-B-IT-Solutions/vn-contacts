package cz.prm.domain.customizations.contact.options;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void newInstance() {
        var value = uuid();
        var product = new Product(value);
        assertThat(product.getValue()).isEqualTo(value);
        assertThat(product.getDescription()).isNull();
    }
}