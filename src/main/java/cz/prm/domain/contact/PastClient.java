package cz.prm.domain.contact;

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
@Table(name = "PAST_CLIENT", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PastClient {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "PAST_CLIENT_SEQ")
    @SequenceGenerator(name = "PAST_CLIENT_SEQ", sequenceName = "PAST_CLIENT_SEQ", allocationSize = 1)
    @Column(name = "PAST_CLIENT_ID")
    private Long pastClientId;

    @Column(name = "NAME")
    private String name;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "PAST_CLIENT_CHARACTERISTICS", joinColumns = @JoinColumn(name = "PAST_CLIENT_ID"))
    @Column(name = "CHARACTERISTIC")
    private List<String> characteristics;

    @Column(name = "PROVIDED_SERVICES")
    private String providedServices;

    @Column(name = "OUTCOMES")
    private String outcomes;

    @Column(name = "_ORDER")
    private Short order;
}
