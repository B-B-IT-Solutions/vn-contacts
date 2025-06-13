package cz.prm.domain.referral;

import static jakarta.persistence.CascadeType.ALL;
import static java.util.Objects.nonNull;

import cz.prm.domain.common.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "REMINDER", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REMINDER_SEQ")
    @SequenceGenerator(name = "REMINDER_SEQ", sequenceName = "REMINDER_SEQ", allocationSize = 1)
    @Column(name = "REMINDER_ID")
    private Long referralId;

    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "recurrence_id")
    private Recurrence recurrence;

    @LastModifiedDate
    @Column(name = "LAST_EDIT_DATE")
    private Instant lastEditDate;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;

    @CreatedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "username", column = @Column(name = "OWNER_USERNAME")),
        @AttributeOverride(name = "email", column = @Column(name = "OWNER_EMAIL"))
    })
    private User owner;

    public boolean hasActiveRecurrence() {
        if (nonNull(recurrence)) {
            return recurrence.hasActiveRule();
        }
        return false;
    }
}
