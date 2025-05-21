package cz.prm.domain.contact;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static java.util.Objects.isNull;

import cz.prm.domain.common.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @OneToMany(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "CONTACT_ID")
    private List<IdealClient> idealClients;

    @OneToMany(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "CONTACT_ID")
    private List<PastClient> pastClients;

    @Column(name = "CONTACT_GOALS", columnDefinition = "TEXT")
    private String contactGoals;

    @Column(name = "CONTACT_CHALLENGES", columnDefinition = "TEXT")
    private String contactChallenges;

    @Column(name = "MY_BENEFITS", columnDefinition = "TEXT")
    private String myBenefits;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "occurrenceDate", column = @Column(name = "FIRST_INTERACTION_OCCURRENCE_DATE")),
        @AttributeOverride(name = "location", column = @Column(name = "FIRST_INTERACTION_LOCATION")),
        @AttributeOverride(name = "comment", column = @Column(name = "FIRST_INTERACTION_COMMENT"))
    })
    private FirstInteraction firstInteraction;

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

    public FirstInteraction getFirstInteraction() {
        if (isNull(firstInteraction)) {
            firstInteraction = new FirstInteraction();
        }
        return firstInteraction;
    }
}
