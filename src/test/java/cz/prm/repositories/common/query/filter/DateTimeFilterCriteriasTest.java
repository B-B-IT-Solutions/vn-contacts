package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class DateTimeFilterCriteriasTest {

    @Test
    void filtersWithoutOperation() {
        var fcs1 = new DateTimeFilterCriterias(null, Instant.class);
        assertThat(fcs1.getCriterias()).isEmpty();

        var fcs2 = new DateTimeFilterCriterias("", Instant.class);
        assertThat(fcs2.getCriterias()).isEmpty();

        var value = uuid();
        var fcs3 = new DateTimeFilterCriterias(value, Instant.class);
        assertThat(fcs3.getCriterias()).isNotEmpty().hasSize(1);

        var fc3 = fcs3.getCriterias().get(0);
        assertThat(fc3.getOperation()).isNull();
        assertThat(fc3.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterRandomOperation() {
        var operation = uuid().replace("-", "");
        var value = uuid();
        var filter = format("%s(%s)", operation, value);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterGreaterThanOperation() {
        var value = uuid();
        var filter = format("greaterThan(%s)", value);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterLessThanOperation() {
        var value = uuid();
        var filter = format("lessThan(%s)", value);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo("lessThan");
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void multipleFiltersRandomOperation() {
        var operation1 = uuid().replace("-", "");
        var operation2 = uuid().replace("-", "");
        var operation3 = uuid().replace("-", "");
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("%s(%s)", operation1, value1);
        var filter2 = format("%s(%s)", operation2, value2);
        var filter3 = format("%s(%s)", operation3, value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo(operation1);
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo(operation2);
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo(operation3);
        assertThat(fc3.getValues()).containsExactly(value3);
    }

    @Test
    void multipleFiltersGreaterThanOperation() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("greaterThan(%s)", value1);
        var filter2 = format("greaterThan(%s)", value2);
        var filter3 = format("greaterThan(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo("greaterThan");
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo("greaterThan");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo("greaterThan");
        assertThat(fc3.getValues()).containsExactly(value3);
    }

    @Test
    void multipleFiltersLessThanOperation() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("lessThan(%s)", value1);
        var filter2 = format("lessThan(%s)", value2);
        var filter3 = format("lessThan(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new DateTimeFilterCriterias(filter, Instant.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo("lessThan");
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo("lessThan");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo("lessThan");
        assertThat(fc3.getValues()).containsExactly(value3);
    }
}