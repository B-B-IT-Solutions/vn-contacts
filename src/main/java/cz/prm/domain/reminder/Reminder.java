package cz.prm.domain.reminder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.NONE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
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
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REMINDER_SEQ")
    @SequenceGenerator(name = "REMINDER_SEQ", sequenceName = "REMINDER_SEQ", allocationSize = 1)
    @Column(name = "REMINDER_ID")
    private Long reminderId;

    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "RECURRENCE")
    private String recurrence;

    @Setter(NONE)
    @Transient
    private RecurrenceRule recurrenceRule;

    @LastModifiedDate
    @Column(name = "LAST_EDIT_DATE")
    private Instant lastEditDate;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;

    @CreatedBy
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "username", column = @Column(name = "OWNER_USERNAME")),
        @AttributeOverride(name = "email", column = @Column(name = "OWNER_EMAIL"))})
    private User owner;

    public RecurrenceRule getRecurrenceRule() {
        if (isNull(recurrenceRule)) {
            createRecurrenceRule();
        }
        return recurrenceRule;
    }

    public boolean hasRecurrenceRule() {
        if (isNull(recurrenceRule)) {
            createRecurrenceRule();
        }
        return nonNull(recurrenceRule);
    }

    private void createRecurrenceRule() {
        if (isNotBlank(recurrence) && isNull(recurrenceRule)) {
            try {
                recurrenceRule = new RecurrenceRule(recurrence);
            } catch (InvalidRecurrenceRuleException e) {
                log.warn("RecurrenceRule is invalid!", e);
            }
        }
    }
}
