package cz.prm.domain.reminder;

import static cz.prm.utils.ReminderUtils.recurrenceRule;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dmfs.rfc5545.DateTime.parse;
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

        r.resetParsedRule();
        r.setValue("RRULEEE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1:FREQ=YEARLY");
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

            r.resetParsedRule();
            r.setValue("DTSTARTTT:invalid\nRRULEEE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            r.resetParsedRule();
            r.setValue("DTSTARTTT:invalid:invalid\nRRULEEE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1:FREQ=YEARLY");
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            dateTimeMock.when(() -> parse(anyString())).thenThrow(IllegalArgumentException.class);
            r.resetParsedRule();
            r.setValue("DTSTART:invalid\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
            assertThat(r.getStartDate()).isEqualTo(dateTime1);

            dateTimeMock.when(() -> parse(anyString())).thenReturn(dateTime2);
            r.resetParsedRule();
            r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
            assertThat(r.getStartDate()).isEqualTo(dateTime2);
        }
    }

    @Test
    void resetParsedRule() {
        var r = new Recurrence();
        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        r.resolveNextOccurrence();
        assertThat(r.getStartDate()).isEqualTo(parse("20241027T104500Z"));
        assertThat(r.getRecurrenceRule()).isNotNull();
        assertThat(r.getNextOccurrence()).isNotNull();

        r.setValue(null);
        r.resetParsedRule();
        assertThat(r.getStartDate()).isNotNull().isNotEqualTo(parse("20241027T104500Z"));
        assertThat(r.getRecurrenceRule()).isNull();
        assertThat(r.getNextOccurrence()).isNull();
    }

    @Test
    void hasActiveRule() {
        var r = new Recurrence();
        assertThat(r.hasActiveRule()).isFalse();

        r.setValue(null);
        assertThat(r.hasActiveRule()).isFalse();

        r.setValue("");
        assertThat(r.hasActiveRule()).isFalse();

        r.setValue(uuid());
        assertThat(r.hasActiveRule()).isFalse();

        r.resetParsedRule();
        r.setValue("DTSTART:20241027T104500Z");
        assertThat(r.hasActiveRule()).isFalse();

        r.resetParsedRule();
        r.setValue("RRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.hasActiveRule()).isTrue();

        r.resetParsedRule();
        r.setValue("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(r.hasActiveRule()).isTrue();
    }

    @Test
    void resolveNextOccurrence() {
        var r = new Recurrence();
        r.setValue("DTSTART:20211027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(parse("20250101T000000Z"));

        r.resetParsedRule();
        r.setValue("DTSTART:20201027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1;COUNT=3");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();
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