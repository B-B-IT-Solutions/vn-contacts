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

        reminder.setRecurrence("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU");
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

        reminder.setRecurrence("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU");
        assertThat(reminder.hasRecurrenceRule()).isTrue();
    }
}