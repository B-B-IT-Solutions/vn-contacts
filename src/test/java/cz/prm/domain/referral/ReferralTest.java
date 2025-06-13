package cz.prm.domain.referral;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ReferralTest {

    @Test
    void hasActiveRecurrence() {
        var reminder = new Referral();
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