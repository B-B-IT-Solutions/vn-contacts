package cz.prm.domain.contact;

import static jakarta.persistence.FetchType.EAGER;

import cz.prm.domain.common.User;
import cz.prm.domain.contact.listeners.ContactAnonymizerListener;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners({AuditingEntityListener.class})
@Entity
@Table(name = "CONTACT", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_SEQ")
    @SequenceGenerator(name = "CONTACT_SEQ", sequenceName = "CONTACT_SEQ", allocationSize = 1)
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "KNOW_SCORE")
    private Integer knowScore;

    @Column(name = "LIKE_SCORE")
    private Integer likeScore;

    @Column(name = "TRUST_SCORE")
    private Integer trustScore;

    @Column(name = "KNOW_LIKE_TRUST_STATUS")
    private String knowLikeTrustStatus;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_TELEPHONE", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "TELEPHONE")
    private List<Connection> telephones;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_EMAIL", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "EMAIL")
    private List<Connection> emails;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_URL", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "URL")
    private List<Connection> urls;

    @Embedded
    private Occupation occupation;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_LABEL", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "LABEL")
    private List<String> labels;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_INDUSTRY", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "INDUSTRY")
    private List<String> industries;

    @Column(name = "DATE_OF_BIRTH")
    private Instant dateOfBirth;

    @CreatedBy
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "username", column = @Column(name = "OWNER_USERNAME")),
        @AttributeOverride(name = "email", column = @Column(name = "OWNER_EMAIL"))})
    private User owner;

    @LastModifiedDate
    @Column(name = "LAST_EDIT_DATE")
    private Instant lastEditDate;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;
}
