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
public class ContactNotifications {

    @Column(name = "CONTACT_STALE_REMINDER")
    private boolean stalenessReminder;
}
