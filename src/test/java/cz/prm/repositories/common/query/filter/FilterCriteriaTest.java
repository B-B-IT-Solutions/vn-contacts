package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
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

    @Test
    void hasBetweenDateValues() {
        var filter = "between()";
        var fc = new FilterCriteria(filter);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        filter = "between(,)";
        fc = new FilterCriteria(filter);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var values = format("%s,%s", uuid(), uuid());
        filter = format("between(%s)", values);
        fc = new FilterCriteria(filter);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var date1 = new Date(value1).toInstant();
        var date2 = new Date(value2).toInstant();

        values = format("%s,%s,%s", value1, value2, value3);
        filter = format("between(%s)", values);
        fc = new FilterCriteria(filter);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        values = format("%s,%s", value1, value2);
        filter = format("between(%s)", values);
        fc = new FilterCriteria(filter);
        assertThat(fc.hasBetweenDateValues()).isTrue();
        assertThat(fc.getDateValues()).containsExactly(date1, date2);
        assertThat(fc.getValues()).containsExactly(value1, value2);
    }

    @Test
    void getDateValues() {
        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var date1 = new Date(value1).toInstant();
        var date2 = new Date(value2).toInstant();
        var date3 = new Date(value3).toInstant();
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("greaterThan(%s)", values);
        var fc = new FilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getDateValues()).containsExactly(date1, date2, date3);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }
}