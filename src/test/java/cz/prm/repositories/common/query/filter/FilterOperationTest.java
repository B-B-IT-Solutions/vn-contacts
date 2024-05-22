package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FilterOperationTest {

    private static final FilterCriteria fcRandom = new FilterCriteria(uuid());
    private static final FilterCriteria fcContains = new FilterCriteria("contains(value1)");
    private static final FilterCriteria fcNotContains = new FilterCriteria("notContains(value2)");

    @Test
    void getName() {
        assertThat(FilterOperation.CONTAINS.getName()).isEqualTo("contains");
        assertThat(FilterOperation.NOT_CONTAINS.getName()).isEqualTo("notContains");
    }

    @Test
    void isOperationContains() {
        assertThat(FilterOperation.CONTAINS.isOperation(fcContains)).isTrue();
        assertThat(FilterOperation.CONTAINS.isOperation(fcNotContains)).isFalse();
        assertThat(FilterOperation.CONTAINS.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationNotContains() {
        assertThat(FilterOperation.NOT_CONTAINS.isOperation(fcNotContains)).isTrue();
        assertThat(FilterOperation.NOT_CONTAINS.isOperation(fcContains)).isFalse();
        assertThat(FilterOperation.NOT_CONTAINS.isOperation(fcRandom)).isFalse();
    }
}