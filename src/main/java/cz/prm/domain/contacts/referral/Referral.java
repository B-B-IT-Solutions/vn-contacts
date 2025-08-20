package cz.prm.domain.contacts.referral;

import static jakarta.persistence.EnumType.ORDINAL;

import cz.prm.domain.common.Priority;
import cz.prm.domain.common.User;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "REFERRAL", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFERRAL_SEQ")
    @SequenceGenerator(name = "REFERRAL_SEQ", sequenceName = "REFERRAL_SEQ", allocationSize = 1)
    @Column(name = "REFERRAL_ID")
    private Long referralId;

    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;

    @Column(name = "SOURCE")
    private String source;

    @Enumerated(value = ORDINAL)
    @Column(name = "PRIORITY")
    private Priority priority;

    @Enumerated(value = ORDINAL)
    @Column(name = "STATUS")
    private ReferralStatus status;

    @Column(name = "CLOSED_DATE")
    private Instant closedDate;

    @Column(name = "START_DATE")
    private Instant startDate;

    @Column(name = "EXPIRED_DATE")
    private Instant expiredDate;

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
}
