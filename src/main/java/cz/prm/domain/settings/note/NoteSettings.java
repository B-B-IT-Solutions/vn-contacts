package cz.prm.domain.settings.note;

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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "NOTE_SETTINGS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTE_SETTINGS_SEQ")
    @SequenceGenerator(name = "NOTE_SETTINGS_SEQ", sequenceName = "NOTE_SETTINGS_SEQ", allocationSize = 1)
    @Column(name = "SETTINGS_ID")
    private Long settingsId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "NOTE_SETTINGS_CATEGORY", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "CATEGORY")
    private List<Category> categories;

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
