package cz.prm.domain.settings.notifications;

import static jakarta.persistence.EnumType.ORDINAL;

import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.GlobalNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//@EntityListeners(AuditingEntityListener.class)
//@Entity
//@Table(name = "NOTIFICATION_SETTINGS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATION_SETTINGS_SEQ")
    @SequenceGenerator(name = "NOTIFICATION_SETTINGS_SEQ", sequenceName = "NOTIFICATION_SETTINGS_SEQ", allocationSize = 1)
    @Column(name = "SETTINGS_ID")
    private Long settingsId;

    @Enumerated(value = ORDINAL)
    @Column(name = "GLOBAL_NOTIFICATIONS_SWITCH")
    private GlobalNotifications global;

    @Embedded
    private ContactNotifications contact;

    @Embedded
    private ReferralNotifications referral;

    @Embedded
    private TaskNotifications task;
}
