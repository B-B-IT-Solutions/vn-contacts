package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FilterCriteriasTest {

    @Test
    void filtersWithoutOperation() {
        var fcs1 = new FilterCriterias(null);
        assertThat(fcs1.getCriterias()).isEmpty();

        var fcs2 = new FilterCriterias("");
        assertThat(fcs2.getCriterias()).isEmpty();

        var value = uuid();
        var fcs3 = new FilterCriterias(value);
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
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterContainsOperation() {
        var value = uuid();
        var filter = format("contains(%s)", value);
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo("contains");
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterNotContainsOperation() {
        var value = uuid();
        var filter = format("notContains(%s)", value);
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getOperation()).isEqualTo("notContains");
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
        var fcs = new FilterCriterias(filter);
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
    void multipleFiltersContainsOperation() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("contains(%s)", value1);
        var filter2 = format("contains(%s)", value2);
        var filter3 = format("contains(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo("contains");
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo("contains");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo("contains");
        assertThat(fc3.getValues()).containsExactly(value3);
    }

    @Test
    void multipleFiltersNotContainsOperation() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("notContains(%s)", value1);
        var filter2 = format("notContains(%s)", value2);
        var filter3 = format("notContains(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo("notContains");
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo("notContains");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo("notContains");
        assertThat(fc3.getValues()).containsExactly(value3);
    }

    @Test
    void multipleFiltersCombinedOperation() {
        var operation1 = uuid().replace("-", "");
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("%s(%s)", operation1, value1);
        var filter2 = format("contains(%s)", value2);
        var filter3 = format("notContains(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new FilterCriterias(filter);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getOperation()).isEqualTo(operation1);
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getOperation()).isEqualTo("contains");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getOperation()).isEqualTo("notContains");
        assertThat(fc3.getValues()).containsExactly(value3);
    }
}