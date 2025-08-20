package cz.prm.domain.contacts.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstInteraction {

    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private String source;

    @Column(name = "date")
    private Instant date;

    @Column(name = "notes")
    private String notes;
}
