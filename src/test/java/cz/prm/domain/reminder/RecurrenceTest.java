package cz.prm.domain.reminder;

import static cz.prm.utils.ReminderUtils.recurrenceRule;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dmfs.rfc5545.DateTime.parse;

import org.dmfs.rfc5545.DateTime;
import org.junit.jupiter.api.Test;

class RecurrenceTest {

    @Test
    void newInstance() {
        var value = uuid();
        var r1 = new Recurrence(value);
        assertThat(r1.getValue()).isEqualTo(value);

        var r2 = new Recurrence();
        assertThat(r2.getValue()).isNull();

        r2.setValue(value);
        assertThat(r1.getValue()).isEqualTo(value);
    }

    @Test
    void getRecurrenceRule() {
        var r = new Recurrence();
        assertThat(r.getRecurrenceRule()).isNull();

        r.setValue(null);
        assertThat(r.getRecurrenceRule()).isNull();

        r.setValue("");
        assertThat(r.getRecurrenceRule()).isNull();

        r.setValue(uuid());
        assertThat(r.getRecurrenceRule()).isNull();

        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.getRecurrenceRule()).isNotNull();
    }

    @Test
    void getStartDate() {
        var r = new Recurrence();
        assertThat(r.getStartDate()).isNull();

        r.setValue(null);
        assertThat(r.getStartDate()).isNull();

        r.setValue("");
        assertThat(r.getStartDate()).isNull();

        r.setValue(uuid());
        assertThat(r.getStartDate()).isNull();

        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.getStartDate()).isNotNull();
        assertThat(r.getStartDate()).isEqualTo(parse("20241027T104500Z"));
    }

    @Test
    void hasActiveRecurrence() {
        var r = new Recurrence();
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue(null);
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue("");
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue(uuid());
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.hasActiveRecurrence()).isTrue();
    }

    @Test
    void isDue() {
        var r = new Recurrence();
        var rrule = recurrenceRule();
        var startDate = DateTime.now();
        var value = String.format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        r.setValue(value);
        assertThat(r.isDue()).isFalse();
    }
}