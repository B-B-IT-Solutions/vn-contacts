package cz.prm.domain.customizations.contact;

import static jakarta.persistence.FetchType.EAGER;

import cz.prm.domain.common.User;
import cz.prm.domain.customizations.contact.options.Industry;
import cz.prm.domain.customizations.contact.options.Label;
import cz.prm.domain.customizations.contact.options.Product;
import cz.prm.domain.customizations.contact.options.Skill;
import cz.prm.domain.customizations.contact.options.TargetMarket;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "CONTACT_SETTINGS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SETTINGS_SEQ")
    @SequenceGenerator(name = "SETTINGS_SEQ", sequenceName = "SETTINGS_SEQ", allocationSize = 1)
    @Column(name = "SETTINGS_ID")
    private Long settingsId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SETTINGS_LABEL", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "LABEL")
    private List<Label> labels;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SETTINGS_INDUSTRY", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "INDUSTRIES")
    private List<Industry> industries;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SETTINGS_SKILL", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "SKILLS")
    private List<Skill> skills;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SETTINGS_PRODUCT", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "PRODUCTS")
    private List<Product> products;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "CONTACT_SETTINGS_TARGET_MARKET", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "TARGET_MARKETS")
    private List<TargetMarket> targetMarkets;

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
