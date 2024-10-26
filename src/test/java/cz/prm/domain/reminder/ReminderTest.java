package cz.prm.domain.reminder;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReminderTest {

    @Test
    void getRecurrenceRule() {
        var reminder = new Reminder();
        assertThat(reminder.getRecurrenceRule()).isNull();

        reminder.setRecurrence(null);
        assertThat(reminder.getRecurrenceRule()).isNull();

        reminder.setRecurrence("");
        assertThat(reminder.getRecurrenceRule()).isNull();

        reminder.setRecurrence(uuid());
        assertThat(reminder.getRecurrenceRule()).isNull();

        reminder.setRecurrence("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(reminder.getRecurrenceRule()).isNotNull();
    }

    @Test
    void hasRecurrenceRule() {
        var reminder = new Reminder();
        assertThat(reminder.hasRecurrenceRule()).isFalse();

        reminder.setRecurrence(null);
        assertThat(reminder.hasRecurrenceRule()).isFalse();

        reminder.setRecurrence("");
        assertThat(reminder.hasRecurrenceRule()).isFalse();

        reminder.setRecurrence(uuid());
        assertThat(reminder.hasRecurrenceRule()).isFalse();

        reminder.setRecurrence("DTSTART:20241027T104500Z\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1");
        assertThat(reminder.hasRecurrenceRule()).isTrue();
    }
}