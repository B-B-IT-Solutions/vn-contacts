package cz.prm.domain.contact;

import static jakarta.persistence.FetchType.EAGER;

import cz.prm.domain.common.User;
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

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "DATE_OF_BIRTH")
    private Instant dateOfBirth;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "TRUST_SCORE")
    private Integer trustScore;

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

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SKILL", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "SKILL")
    private List<String> skills;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_PRODUCT", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "PRODUCT")
    private List<String> products;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_TARGET_MARKET", joinColumns = @JoinColumn(name = "CONTACT_ID"))
    @Column(name = "TARGET_MARKET")
    private List<String> targetMarkets;

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

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;
}
