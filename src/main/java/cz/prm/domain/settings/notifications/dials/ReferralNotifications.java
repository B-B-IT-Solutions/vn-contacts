package cz.prm.domain.settings.notifications.dials;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralNotifications {

    @Column(name = "REFERRAL_FOLLOWUP_REMINDER")
    private boolean followupReminder;

    @Column(name = "REFERRAL_EXPIRY_REMINDER")
    private boolean expiryReminder;

    @Column(name = "REFERRAL_STALE_REMINDER")
    private boolean stalenessReminder;
}
