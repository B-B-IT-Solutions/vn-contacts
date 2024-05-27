package cz.prm.domain.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "NOTE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTE_SEQ")
    @SequenceGenerator(name = "NOTE_SEQ", sequenceName = "NOTE_SEQ", allocationSize = 1)
    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Lob
    @Column(name = "TEXT")
    private String text;

    @LastModifiedDate
    @Column(name = "LAST_EDIT_DATE")
    private Instant lastEditDate;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;

    @Column(name = "OWNER")
    private String owner;
}
