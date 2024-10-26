package cz.prm.domain.reminder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ReminderTest {

    @Test
    void hasActiveRecurrence() {
        var reminder = new Reminder();
        assertThat(reminder.hasActiveRecurrence()).isFalse();

        var recurrence = mock(Recurrence.class);
        when(recurrence.hasActiveRecurrence()).thenReturn(false);

        reminder.setRecurrence(recurrence);
        assertThat(reminder.hasActiveRecurrence()).isFalse();

        when(recurrence.hasActiveRecurrence()).thenReturn(true);
        assertThat(reminder.hasActiveRecurrence()).isTrue();
    }
}