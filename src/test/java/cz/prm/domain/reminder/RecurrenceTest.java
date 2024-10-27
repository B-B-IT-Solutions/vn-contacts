package cz.prm.domain.reminder;

import static cz.prm.utils.ReminderUtils.recurrenceRule;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

import org.dmfs.rfc5545.DateTime;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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

        r.setValue("DTSTART:20241027T104500Z\nRRULE:invalid");
        assertThat(r.getRecurrenceRule()).isNull();

        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.getRecurrenceRule()).isNotNull();
    }

    @Test
    void getStartDate() {
        var dateTime1 = new DateTime(randomLong());
        var dateTime2 = new DateTime(randomLong());
        try (MockedStatic<DateTime> dateTimeMock = Mockito.mockStatic(DateTime.class)) {
            dateTimeMock.when(() -> DateTime.now()).thenReturn(dateTime1);

            var r = new Recurrence();
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            r.setValue(null);
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            r.setValue("");
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            dateTimeMock.when(() -> DateTime.parse(anyString())).thenThrow(IllegalArgumentException.class);
            r.resetRule();
            r.setValue("DTSTART:invalid\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            dateTimeMock.when(() -> DateTime.parse(anyString())).thenReturn(dateTime2);
            r.resetRule();
            r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
            assertThat(r.getStartDate()).isEqualTo(dateTime2);
        }
    }

    @Test
    void hasActiveRecurrence() {
        var r = new Recurrence();
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue(null);
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.setValue("");
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.resetRule();
        r.setValue(uuid());
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.resetRule();
        r.setValue("DTSTART:20241027T104500Z");
        assertThat(r.hasActiveRecurrence()).isFalse();

        r.resetRule();
        r.setValue("RRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.hasActiveRecurrence()).isTrue();

        r.resetRule();
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