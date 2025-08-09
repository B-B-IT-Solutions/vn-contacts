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

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "type", column = @Column(name = "FIRST_INTERACTION_TYPE")),
        @AttributeOverride(name = "source", column = @Column(name = "FIRST_INTERACTION_SOURCE")),
        @AttributeOverride(name = "date", column = @Column(name = "FIRST_INTERACTION_DATE")),
        @AttributeOverride(name = "notes", column = @Column(name = "FIRST_INTERACTION_NOTES"))
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
