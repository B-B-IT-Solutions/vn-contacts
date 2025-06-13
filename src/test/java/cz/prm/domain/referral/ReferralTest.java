package cz.prm.domain.referral;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ReferralTest {

    @Test
    void hasActiveRecurrence() {
        var referral = new Referral();
        assertThat(referral.hasActiveRecurrence()).isFalse();

        referral.setRecurrence(null);
        assertThat(referral.hasActiveRecurrence()).isFalse();

        var recurrence = mock(Recurrence.class);
        when(recurrence.hasActiveRule()).thenReturn(false);

        referral.setRecurrence(recurrence);
        assertThat(referral.hasActiveRecurrence()).isFalse();

        when(recurrence.hasActiveRule()).thenReturn(true);
        assertThat(referral.hasActiveRecurrence()).isTrue();
    }
}