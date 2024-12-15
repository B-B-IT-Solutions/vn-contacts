package cz.prm.repositories.common.query.filter;

import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TimeUtils.toLocalDate;
import static cz.prm.utils.TimeUtils.toLocalDateTime;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import org.junit.jupiter.api.Test;

class TemporalFilterCriteriaTest {

    @Test
    void hasBetweenDateValues_Instant() {
        var filter = "between()";
        var fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        filter = "between(,)";
        fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var values = format("%s,%s", uuid(), uuid());
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";

        values = format("%s,%s,%s", value1, value2, value3);
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        values = format("%s,%s", value1, value2);
        filter = format("between(%s)", values);
        var date1 = new Date(value1).toInstant();
        var date2 = new Date(value2).toInstant();

        fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.hasBetweenDateValues()).isTrue();
        assertThat(fc.getDateValues()).containsExactly(date1, date2);
        assertThat(fc.getValues()).containsExactly(value1, value2);
    }

    @Test
    void hasBetweenDateValues_LocalDate() {
        var filter = "between()";
        var fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        filter = "between(,)";
        fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var values = format("%s,%s", uuid(), uuid());
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";

        values = format("%s,%s,%s", value1, value2, value3);
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        values = format("%s,%s", value1, value2);
        filter = format("between(%s)", values);
        var date1 = toLocalDate(value1);
        var date2 = toLocalDate(value2);

        fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.hasBetweenDateValues()).isTrue();
        assertThat(fc.getDateValues()).containsExactly(date1, date2);
        assertThat(fc.getValues()).containsExactly(value1, value2);
    }

    @Test
    void hasBetweenDateValues_LocalDateTime() {
        var filter = "between()";
        var fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        filter = "between(,)";
        fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var values = format("%s,%s", uuid(), uuid());
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";

        values = format("%s,%s,%s", value1, value2, value3);
        filter = format("between(%s)", values);
        fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.hasBetweenDateValues()).isFalse();

        values = format("%s,%s", value1, value2);
        filter = format("between(%s)", values);
        var date1 = toLocalDateTime(value1);
        var date2 = toLocalDateTime(value2);

        fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.hasBetweenDateValues()).isTrue();
        assertThat(fc.getDateValues()).containsExactly(date1, date2);
        assertThat(fc.getValues()).containsExactly(value1, value2);
    }

    @Test
    void getDateValues_Instant() {
        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var date1 = new Date(value1).toInstant();
        var date2 = new Date(value2).toInstant();
        var date3 = new Date(value3).toInstant();
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("greaterThan(%s)", values);
        var fc = new TemporalFilterCriteria(filter, Instant.class);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getDateValues()).containsExactly(date1, date2, date3);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void getDateValues_LocalDate() {
        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var date1 = toLocalDate(value1);
        var date2 = toLocalDate(value2);
        var date3 = toLocalDate(value3);
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("greaterThan(%s)", values);
        var fc = new TemporalFilterCriteria(filter, LocalDate.class);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getDateValues()).containsExactly(date1, date2, date3);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void getDateValues_LocalDateTime() {
        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var date1 = toLocalDateTime(value1);
        var date2 = toLocalDateTime(value2);
        var date3 = toLocalDateTime(value3);
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("greaterThan(%s)", values);
        var fc = new TemporalFilterCriteria(filter, LocalDateTime.class);
        assertThat(fc.getOperation()).isEqualTo("greaterThan");
        assertThat(fc.getDateValues()).containsExactly(date1, date2, date3);
        assertThat(fc.getValues()).containsExactly(value1, value2, value3);
    }

    @Test
    void getDateValues_UknownFilterType() {
        var value1 = "15 Dec 2024";
        var value2 = "17 Dec 2024";
        var value3 = "19 Dec 2024";
        var values = format("%s,%s,%s", value1, value2, value3);
        var filter = format("greaterThan(%s)", values);
        var fc = new TemporalFilterCriteria(filter, OffsetDateTime.class);
        assertThrows(IllegalArgumentException.class, () -> fc.getDateValues());
    }
}