package cz.prm.domain.contact;

import static java.util.Objects.isNull;

import cz.prm.domain.common.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ABOUT", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class About {

    @Id
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "occurrenceDate", column = @Column(name = "FIRST_MEETING_OCCURRENCE_DATE")),
        @AttributeOverride(name = "location", column = @Column(name = "FIRST_MEETING_LOCATION")),
        @AttributeOverride(name = "comment", column = @Column(name = "FIRST_MEETING_COMMENT"))
    })
    private Meeting firstMeeting;

    @CreatedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "username", column = @Column(name = "OWNER_USERNAME")),
        @AttributeOverride(name = "email", column = @Column(name = "OWNER_EMAIL"))
    })
    private User owner;

    public About(Long contactId) {
        this.contactId = contactId;
    }

    public Meeting getFirstMeeting() {
        if (isNull(firstMeeting)) {
            firstMeeting = new Meeting();
        }
        return firstMeeting;
    }
}
