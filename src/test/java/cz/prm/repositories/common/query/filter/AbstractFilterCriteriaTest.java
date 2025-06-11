package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AbstractFilterCriteriaTest {

    @Test
    void filterWithoutOperation() {
        var fc1 = new DummyFilterCriteria(null);
        assertThat(fc1.getOperation()).isNull();
        assertThat(fc1.getValues()).isEmpty();

        var fc2 = new DummyFilterCriteria("");
        assertThat(fc2.getOperation()).isNull();
        assertThat(fc2.getValues()).containsExactly("");

        var value1 = uuid();
        var fc3 = new DummyFilterCriteria(value1);
        assertThat(fc3.getOperation()).isNull();
        assertThat(fc3.getValues()).containsExactly(value1);

        var value2 = uuid();
        var fc4 = new DummyFilterCriteria(format("%s,%s", value1, value2));
        assertThat(fc4.getOperation()).isNull();
        assertThat(fc4.getValues()).hasSize(2).containsExactly(value1, value2);

        var value3 = uuid();
        var fc5 = new DummyFilterCriteria(format("%s,%s,%s", value1, value2, value3));
        assertThat(fc5.getOperation()).isNull();
        assertThat(fc5.getValues()).hasSize(3).containsExactly(value1, value2, value3);
    }

    @Test
    void filterRandomOperationOneValue() {
        var operation = uuid().replace("-", "");
        var value = uuid();
        var filter = format("%s(%s)", operation, value);
        var fc = new DummyFilterCriteria(filter);
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
        var fc = new DummyFilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void filterContainsOperationOneValue() {
        var value = uuid();
        var filter = format("contains(%s)", value);
        var fc = new DummyFilterCriteria(filter);
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
        var fc = new DummyFilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("contains");
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void filterNotContainsOperationOneValue() {
        var value = uuid();
        var filter = format("notContains(%s)", value);
        var fc = new DummyFilterCriteria(filter);
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
        var fc = new DummyFilterCriteria(filter);
        assertThat(fc.getOperation()).isEqualTo("notContains");
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    private class DummyFilterCriteria extends AbstractFilterCriteria {

        public DummyFilterCriteria(String filter) {
            super(filter);
        }
    }
}