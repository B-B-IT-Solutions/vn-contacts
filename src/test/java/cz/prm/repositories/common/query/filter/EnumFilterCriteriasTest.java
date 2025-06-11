package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.task.Priority;
import cz.prm.domain.task.Status;
import org.junit.jupiter.api.Test;

class EnumFilterCriteriasTest {

    @Test
    void filtersWithoutOperation() {
        var fcs1 = new EnumFilterCriterias(null, Status.class);
        assertThat(fcs1.getCriterias()).isEmpty();

        var fcs2 = new EnumFilterCriterias("", Status.class);
        assertThat(fcs2.getCriterias()).isEmpty();

        var value = uuid();
        var fcs3 = new EnumFilterCriterias(value, Status.class);
        assertThat(fcs3.getCriterias()).isNotEmpty().hasSize(1);

        var fc3 = fcs3.getCriterias().get(0);
        assertThat(fc3.getFilterType()).isEqualTo(Status.class);
        assertThat(fc3.getOperation()).isNull();
        assertThat(fc3.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterRandomOperation() {
        var operation = uuid().replace("-", "");
        var value = uuid();
        var filter = format("%s(%s)", operation, value);
        var fcs = new EnumFilterCriterias(filter, Priority.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getFilterType()).isEqualTo(Priority.class);
        assertThat(fc.getOperation()).isEqualTo(operation);
        assertThat(fc.getValues()).containsExactly(value);
    }

    @Test
    void oneFilterArrIncludesOperation() {
        var value = uuid();
        var filter = format("arrIncludes(%s)", value);
        var fcs = new EnumFilterCriterias(filter, Status.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(1);

        var fc = fcs.getCriterias().get(0);
        assertThat(fc.getFilterType()).isEqualTo(Status.class);
        assertThat(fc.getOperation()).isEqualTo("arrIncludes");
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
        var fcs = new EnumFilterCriterias(filter, Priority.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getFilterType()).isEqualTo(Priority.class);
        assertThat(fc1.getOperation()).isEqualTo(operation1);
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getFilterType()).isEqualTo(Priority.class);
        assertThat(fc2.getOperation()).isEqualTo(operation2);
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getFilterType()).isEqualTo(Priority.class);
        assertThat(fc3.getOperation()).isEqualTo(operation3);
        assertThat(fc3.getValues()).containsExactly(value3);
    }

    @Test
    void multipleFiltersArrIncludesOperation() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var filter1 = format("arrIncludes(%s)", value1);
        var filter2 = format("arrIncludes(%s)", value2);
        var filter3 = format("arrIncludes(%s)", value3);
        var filter = format("%s+%s+%s", filter1, filter2, filter3);
        var fcs = new EnumFilterCriterias(filter, Status.class);
        assertThat(fcs.getCriterias()).isNotEmpty().hasSize(3);

        var fc1 = fcs.getCriterias().get(0);
        assertThat(fc1.getFilterType()).isEqualTo(Status.class);
        assertThat(fc1.getOperation()).isEqualTo("arrIncludes");
        assertThat(fc1.getValues()).containsExactly(value1);

        var fc2 = fcs.getCriterias().get(1);
        assertThat(fc2.getFilterType()).isEqualTo(Status.class);
        assertThat(fc2.getOperation()).isEqualTo("arrIncludes");
        assertThat(fc2.getValues()).containsExactly(value2);

        var fc3 = fcs.getCriterias().get(2);
        assertThat(fc3.getFilterType()).isEqualTo(Status.class);
        assertThat(fc3.getOperation()).isEqualTo("arrIncludes");
        assertThat(fc3.getValues()).containsExactly(value3);
    }
}