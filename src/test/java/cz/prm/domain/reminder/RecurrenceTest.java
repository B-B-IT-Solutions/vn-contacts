package cz.prm.domain.reminder;

import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dmfs.rfc5545.DateTime.parse;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.Instant;
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
    void resolveNextOccurrenceInfinite() {
        var r = new Recurrence();
        r.setValue(null);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();

        var now = DateTime.now();
        var year = now.getYear();

        r.resetParsedRule();
        r.setValue("DTSTART:20211027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year + 1, 1, 1));

        var month = now.getMonth() + 1;
        var day = now.getDayOfMonth();
        r.resetParsedRule();
        r.setValue("DTSTART:20211027T104500Z\nRRULE:FREQ=MONTHLY");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        r.setValue("DTSTART:20211027T104500Z\nRRULE:FREQ=DAILY");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        var rrule = rrule(year + 5, month, day, "RRULE:FREQ=DAILY");
        r.setValue(rrule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year + 5, month, day));
        r.resetParsedRule();
    }

    @Test
    void resolveNextOccurrenceFinite() {
        var r = new Recurrence();
        r.setValue(null);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();

        r.resetParsedRule();
        r.setValue("DTSTART:20201027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1;COUNT=3");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();

        var now = DateTime.now();
        var year = now.getYear();
        var month = now.getMonth() + 1;
        var day = now.getDayOfMonth();

        r.resetParsedRule();
        var ruleYear = month < 11 && day < 21 ? year + 1 : year;
        var rule = rrule(ruleYear, month, day, "RRULE:FREQ=YEARLY;BYMONTH=11;BYMONTHDAY=21;COUNT=3");
        r.setValue(rule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year + 1, 11, 21));

        r.resetParsedRule();
        r.setValue("DTSTART:20210721T104500Z\nRRULE:FREQ=MONTHLY;COUNT=30");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();

        r.resetParsedRule();
        r.setValue("DTSTART:20210721T104500Z\nRRULE:FREQ=DAILY;COUNT=300");
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isNull();

        r.resetParsedRule();
        rule = rrule(year, month, day, "RRULE:FREQ=DAILY;COUNT=300");
        r.setValue(rule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        var startDate = dtstart(year - 3, 10, day);
        var until = date(year + 1, 10, 31);
        var rrule = rrule(startDate, "FREQ=DAILY;", until);
        r.setValue(rrule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        startDate = dtstart(year - 3, month, day);
        until = date(year + 1, month, 31);
        rrule = rrule(startDate, "FREQ=DAILY;", until);
        r.setValue(rrule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        startDate = dtstart(year - 1, 10, day);
        until = date(year + 1, 10, 31);
        rrule = rrule(startDate, "FREQ=MONTHLY;", until);
        r.setValue(rrule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        startDate = dtstart(year - 1, month, day);
        until = date(year + 1, month, 31);
        rrule = rrule(startDate, "FREQ=MONTHLY;", until);
        r.setValue(rrule);
        r.resolveNextOccurrence();
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, month, day));

        r.resetParsedRule();
        startDate = dtstart(year - 1, month, 15);
        until = date(year + 1, month, 31);
        rrule = rrule(startDate, "FREQ=MONTHLY;", until);
        r.setValue(rrule);
        r.resolveNextOccurrence();
        var eMonth = day > 15 ? month + 1 : month;
        assertThat(r.getNextOccurrence()).isEqualTo(toInstant(year, eMonth, 15));
    }

    private String rrule(long year, int month, int day, String rule) {
        var dt = date(year, month, day);
        return format("DTSTART:%s\n%s", dt, rule);
    }

    private String rrule(String startDate, String rule, String until) {
        return format("%s\nRRULE:%sUNTIL=%s;", startDate, rule, until);
    }

    private String dtstart(long year, int month, int day) {
        var dt = date(year, month, day);
        return format("DTSTART:%s", dt);
    }

    private String date(long year, int month, int day) {
        var sMonth = month < 10 ? "0" + month : month;
        var sDay = day < 10 ? "0" + day : day;
        return format("%s%s%sT000000Z", year, sMonth, sDay);
    }

    private Instant toInstant(long year, int month, int day) {
        return ofEpochMilli(parse(date(year, month, day)).getTimestamp());
    }
}