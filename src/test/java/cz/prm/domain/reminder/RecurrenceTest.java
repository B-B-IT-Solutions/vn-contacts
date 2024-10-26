package cz.prm.domain.reminder;

import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RecurrenceTest {

    @Test
    void getRecurrenceRule() {
        var r = new Recurrence();
        assertThat(r.getRecurrenceRule()).isNull();

        r.setRecurrence(null);
        assertThat(r.getRecurrenceRule()).isNull();

        r.setRecurrence("");
        assertThat(r.getRecurrenceRule()).isNull();

        r.setRecurrence(uuid());
        assertThat(r.getRecurrenceRule()).isNull();

        r.setRecurrence("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.getRecurrenceRule()).isNotNull();
    }

    @Test
    void getStartDate() {
        var r = new Recurrence();
        assertThat(r.getStartDate()).isNull();

        r.setRecurrence(null);
        assertThat(r.getStartDate()).isNull();

        r.setRecurrence("");
        assertThat(r.getStartDate()).isNull();

        r.setRecurrence(uuid());
        assertThat(r.getStartDate()).isNull();

        r.setRecurrence("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.getStartDate()).isNotNull();
        assertThat(r.getStartDate()).isEqualTo(parse("2024-10-26T22:00:00Z"));
    }

    @Test
    void hasValidRecurrence() {
        var r = new Recurrence();
        assertThat(r.hasValidRecurrence()).isFalse();

        r.setRecurrence(null);
        assertThat(r.hasValidRecurrence()).isFalse();

        r.setRecurrence("");
        assertThat(r.hasValidRecurrence()).isFalse();

        r.setRecurrence(uuid());
        assertThat(r.hasValidRecurrence()).isFalse();

        r.setRecurrence("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.hasValidRecurrence()).isTrue();
    }
}