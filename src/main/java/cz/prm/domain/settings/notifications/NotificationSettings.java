package cz.prm.domain.settings.notifications;

import static jakarta.persistence.EnumType.ORDINAL;

import cz.prm.domain.common.User;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.GlobalNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "NOTIFICATION_SETTINGS", schema = "public")
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

    @CreatedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "username", column = @Column(name = "OWNER_USERNAME")),
        @AttributeOverride(name = "email", column = @Column(name = "OWNER_EMAIL"))
    })
    private User owner;

    @LastModifiedDate
    @Column(name = "LAST_EDIT_DATE")
    private Instant lastEditDate;
}
