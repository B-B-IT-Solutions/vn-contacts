package cz.prm.domain.contacts.contact;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "IDEAL_CLIENT", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdealClient {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "IDEAL_CLIENT_SEQ")
    @SequenceGenerator(name = "IDEAL_CLIENT_SEQ", sequenceName = "IDEAL_CLIENT_SEQ", allocationSize = 1)
    @Column(name = "IDEAL_CLIENT_ID")
    private Long idealClientId;

    @Column(name = "NAME")
    private String name;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "IDEAL_CLIENT_CHARACTERISTICS", joinColumns = @JoinColumn(name = "IDEAL_CLIENT_ID"))
    @Column(name = "CHARACTERISTIC")
    private List<String> characteristics;

    @Column(name = "NEEDS")
    private String needs;

    @Column(name = "GOALS")
    private String goals;

    @Column(name = "_ORDER")
    private Short order;
}
