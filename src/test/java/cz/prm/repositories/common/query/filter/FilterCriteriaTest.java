package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FilterCriteriaTest {

    @Test
    void filterWithoutOperation() {
        var fc1 = new FilterCriteria(null);
        assertThat(fc1.getOperation()).isNull();
        assertThat(fc1.getValues()).isEmpty();

        var fc2 = new FilterCriteria("");
        assertThat(fc2.getOperation()).isNull();
        assertThat(fc2.getValues()).containsExactly("");

        var value = uuid();
        var fc3 = new FilterCriteria(value);
        assertThat(fc3.getOperation()).isNull();
        assertThat(fc3.getValues()).containsExactly(value);
    }

    @Test
    void filterRandomOperationOneValue() {
        var operation = uuid().replace("-", "");
        var value = uuid();
        var filter = format("%s(%s)", operation, value);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void filterRandomOperationMultipleValues() {
        var operation = uuid().replace("-", "");
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("%s(%s)", operation, values);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void filterContainsOperationOneValue() {
        var value = uuid();
        var filter = format("contains(%s)", value);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("contains");
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void filterContainsOperationMultipleValues() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("contains(%s)", values);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("contains");
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void filterNotContainsOperationOneValue() {
        var value = uuid();
        var filter = format("notContains(%s)", value);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("notContains");
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void filterNotContainsOperationMultipleValues() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("notContains(%s)", values);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("notContains");
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }
}