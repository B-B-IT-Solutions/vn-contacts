package cz.prm.domain.contacts.reminder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cz.prm.domain.contacts.recurrence.Recurrence;
import cz.prm.domain.contacts.reminder.Reminder;
import org.junit.jupiter.api.Test;

class ReminderTest {

    @Test
    void hasActiveRecurrence() {
        var reminder = new Reminder();
        assertThat(reminder.hasActiveRecurrence()).isFalse();

        reminder.setRecurrence(null);
        assertThat(reminder.hasActiveRecurrence()).isFalse();

        var recurrence = mock(Recurrence.class);
        when(recurrence.hasActiveRule()).thenReturn(false);

        reminder.setRecurrence(recurrence);
        assertThat(reminder.hasActiveRecurrence()).isFalse();

        when(recurrence.hasActiveRule()).thenReturn(true);
        assertThat(reminder.hasActiveRecurrence()).isTrue();
    }
}