package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateTimeFilterCriteriaTest {

    @Test
    void hasBetweenDateValues() {
        var filter = "between()";
        var fc = new DateTimeFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        filter = "between(,)";
        fc = new DateTimeFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var values = format("%s,%s", uuid(), uuid());
        filter = format("between(%s)", values);
        fc = new DateTimeFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";

        values = format("%s,%s,%s", value1, value2, value3);
        filter = format("between(%s)", values);
        fc = new DateTimeFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        values = format("%s,%s", value1, value2);
        filter = format("between(%s)", values);
        var date1 = new Date(value1).toInstant();
        var date2 = new Date(value2).toInstant();

        fc = new DateTimeFilterCriteria(filter, Instant.class);
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
        var fc = new DateTimeFilterCriteria(filter, Instant.class);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getDateValues()).containsExactly(date1, date2, date3);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }
}